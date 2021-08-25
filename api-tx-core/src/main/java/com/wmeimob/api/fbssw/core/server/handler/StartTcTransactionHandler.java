package com.wmeimob.api.fbssw.core.server.handler;

import com.wmeimob.api.fbssw.core.compensation.command.TcCompensationCommand;
import com.wmeimob.api.fbssw.core.concurrent.threadlocal.TcTransactionLocal;
import com.wmeimob.api.fbssw.core.server.TcManagerMessageService;
import com.wmeimob.api.fbssw.core.server.TcTransactionHandler;
import com.wmeimon.api.fbssw.bean.TcTransactionInfo;
import com.wmeimon.api.fbssw.enums.PropagationEnum;
import com.wmeimon.api.fbssw.enums.TransactionRoleEnum;
import com.wmeimon.api.fbssw.enums.TransactionStatusEnum;
import com.wmeimon.api.fbssw.holder.DateUtils;
import com.wmeimon.api.fbssw.holder.IdWorkerUtils;
import com.wmeimon.api.fbssw.netty.TcTransactionGroup;
import com.wmeimon.api.fbssw.netty.TcTransactionItem;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:12
 */
public class StartTcTransactionHandler implements TcTransactionHandler {

    private static final Logger logger = LoggerFactory.getLogger(StartTcTransactionHandler.class);

    @Autowired
    private TcManagerMessageService tcManagerMessageService;
    @Autowired
    private TcCompensationCommand tcCompensationCommand;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    public Object handler(ProceedingJoinPoint point, TcTransactionInfo info) throws Throwable {
        logger.info( "tc-transaction start,  事务发起类：{}",
                point.getTarget().getClass());

        final String groupId = IdWorkerUtils.getInstance().createGroupId();

        //设置事务组ID
        TcTransactionLocal.getInstance().setTcGroupId(groupId);

        final String waitKey = IdWorkerUtils.getInstance().createTaskKey();

        //创建事务组信息
        final Boolean success = tcManagerMessageService.saveTcTransactionGroup(newTcTransactionGroup(groupId, waitKey, info));
        if (success) {
            //如果发起方没有事务
            if (info.getPropagationEnum().getValue() ==
                    PropagationEnum.PROPAGATION_NEVER.getValue()) {
                try {
                    final Object res = point.proceed();

                    final Boolean commit = tcManagerMessageService.preCommitTxTransaction(groupId);
                    if (commit) {
                        //通知tm完成事务
                        CompletableFuture.runAsync(() ->
                                tcManagerMessageService
                                        .asyncCompleteCommit(groupId, waitKey,
                                                TransactionStatusEnum.COMMIT.getCode(),res));
                    }
                    return res;
                } catch (Throwable throwable) {
                    //通知tm整个事务组失败，需要回滚，（回滚那些正常提交的模块，他们正在等待通知。。。。）
                    tcManagerMessageService.rollBackTxTransaction(groupId);
                    throwable.printStackTrace();
                    throw throwable;
                }
            }

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus transactionStatus = platformTransactionManager.getTransaction(def);
            try {
                //发起调用
                final Object res = point.proceed();

                //保存本地补偿数据
                String compensateId = tcCompensationCommand.saveTxCompensation(info.getInvocation(), groupId, waitKey);

                final Boolean commit = tcManagerMessageService.preCommitTxTransaction(groupId);
                if (commit) {
                    //我觉得到这一步了，应该是切面走完，然后需要提交了，此时应该都是进行提交的

                    //提交事务
                    platformTransactionManager.commit(transactionStatus);

                    //删除补偿信息
                    tcCompensationCommand.removeTxCompensation(compensateId);

                    //通知tm完成事务
                    CompletableFuture.runAsync(() ->
                            tcManagerMessageService
                                    .asyncCompleteCommit(groupId, waitKey,
                                            TransactionStatusEnum.COMMIT.getCode(),res));

                } else {
                    logger.error( "预提交失败!");
                    platformTransactionManager.rollback(transactionStatus);
                }
                logger.info( "tx-transaction end,  事务发起类：{0}",
                        point.getTarget().getClass());
                return res;

            } catch (final Throwable throwable) {
                //如果有异常 当前项目事务进行回滚 ，同时通知tm 整个事务失败
                platformTransactionManager.rollback(transactionStatus);

                //通知tm整个事务组失败，需要回滚，（回滚那些正常提交的模块，他们正在等待通知。。。。）
                tcManagerMessageService.rollBackTxTransaction(groupId);

                throwable.printStackTrace();
                throw throwable;
            } finally {
                TcTransactionLocal.getInstance().removeTcGroupId();
            }
        } else {
            throw new RuntimeException("分布式微服务连接异常，创建事务组失败！");
        }
    }

    private TcTransactionGroup newTcTransactionGroup(String groupId, String taskKey, TcTransactionInfo info) {
        //创建事务组信息
        TcTransactionGroup tcTransactionGroup = new TcTransactionGroup();
        tcTransactionGroup.setId(groupId);

        List<TcTransactionItem> items = new ArrayList<>(2);

        TcTransactionItem groupItem = new TcTransactionItem();

        //整个事务组状态为开始
        groupItem.setStatus(TransactionStatusEnum.BEGIN.getCode());

        //设置事务id为组的id  即为 hashKey
        groupItem.setTransId(groupId);
        groupItem.setTaskKey(groupId);
        groupItem.setCreateDate(DateUtils.getCurrentDateTime());

        //设置执行类名称
        groupItem.setTargetClass(info.getInvocation().getTargetClazz().getName());
        //设置执行类方法
        groupItem.setTargetMethod(info.getInvocation().getMethod());

        groupItem.setRole(TransactionRoleEnum.GROUP.getCode());


        items.add(groupItem);

        TcTransactionItem item = new TcTransactionItem();
        item.setTaskKey(taskKey);
        item.setTransId(IdWorkerUtils.getInstance().createUUID());
        item.setRole(TransactionRoleEnum.START.getCode());
        item.setStatus(TransactionStatusEnum.BEGIN.getCode());
        item.setTxGroupId(groupId);

        //设置事务最大等待时间
        item.setWaitMaxTime(info.getWaitMaxTime());
        //设置创建时间
        item.setCreateDate(DateUtils.getCurrentDateTime());
        //设置执行类名称
        item.setTargetClass(info.getInvocation().getTargetClazz().getName());
        //设置执行类方法
        item.setTargetMethod(info.getInvocation().getMethod());

        items.add(item);

        tcTransactionGroup.setItemList(items);
        return tcTransactionGroup;
    }
}
