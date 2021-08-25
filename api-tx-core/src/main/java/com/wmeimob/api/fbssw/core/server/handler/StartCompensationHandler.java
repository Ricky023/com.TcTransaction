package com.wmeimob.api.fbssw.core.server.handler;

import com.wmeimob.api.fbssw.core.concurrent.threadlocal.CompensationLocal;
import com.wmeimob.api.fbssw.core.concurrent.threadlocal.TcTransactionLocal;
import com.wmeimob.api.fbssw.core.server.TcTransactionHandler;
import com.wmeimon.api.fbssw.bean.TcTransactionInfo;
import com.wmeimon.api.fbssw.constant.CommonConstant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-21 23:39
 */
@Component
public class StartCompensationHandler implements TcTransactionHandler {

    private static final Logger logger = LoggerFactory.getLogger(StartCompensationHandler.class);

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    public Object handler(ProceedingJoinPoint point, TcTransactionInfo info) throws Throwable {

        TcTransactionLocal.getInstance().setTcGroupId(CommonConstant.COMPENSATE_ID);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(def);
        try {
            final Object proceed = point.proceed();
            platformTransactionManager.commit(transactionStatus);
            logger.info( "补偿事务执行成功!事务组id为:{0}", info.getTcGroupId());
            return proceed;
        } catch (Throwable e) {
            logger.info( "补偿事务执行失败!事务组id为:{0}", info.getTcGroupId());
            platformTransactionManager.rollback(transactionStatus);
            throw e;
        } finally {
            TcTransactionLocal.getInstance().removeTcGroupId();
            CompensationLocal.getInstance().removeCompensationId();
        }
    }
}
