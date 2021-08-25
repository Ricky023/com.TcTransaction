package com.wmeimob.api.fbssw.core.server.message;

import com.wmeimob.api.fbssw.core.netty.handler.NettyClientMessageHandler;
import com.wmeimob.api.fbssw.core.server.TcManagerMessageService;
import com.wmeimon.api.fbssw.enums.NettyMessageActionEnum;
import com.wmeimon.api.fbssw.enums.TransactionStatusEnum;
import com.wmeimon.api.fbssw.netty.HeartBeat;
import com.wmeimon.api.fbssw.netty.TcTransactionGroup;
import com.wmeimon.api.fbssw.netty.TcTransactionItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-08-11 15:53
 */
@Service
public class NettyMessageServiceImpl implements TcManagerMessageService {

    private static final Logger logger = LoggerFactory.getLogger(NettyMessageServiceImpl.class);

    @Autowired
    private NettyClientMessageHandler nettyClientMessageHandler;


    @Override
    public Boolean saveTcTransactionGroup(TcTransactionGroup tcTransactionGroup) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.CREATE_GROUP.getCode());
        heartBeat.setTxTransactionGroup(tcTransactionGroup);
        Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Boolean) object;
        }

        int x = 0;
        while (x<5){
            x++;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
            if (Objects.nonNull(object)) {
                return (Boolean) object;
            }
            logger.info("第{0}次重试保存事务组操作",x);
        }

        return false;
    }

    @Override
    public Boolean preCommitTxTransaction(String tcGroupId) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.PRE_COMMIT.getCode());
        TcTransactionGroup tcTransactionGroup = new TcTransactionGroup();
        tcTransactionGroup.setStatus(TransactionStatusEnum.PRE_COMMIT.getCode());
        tcTransactionGroup.setId(tcGroupId);

        heartBeat.setTxTransactionGroup(tcTransactionGroup);
        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Boolean) object;
        }
        return false;
    }

    @Override
    public void asyncCompleteCommit(String txGroupId, String taskKey, int status, Object message) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.COMPLETE_COMMIT.getCode());
        TcTransactionGroup tcTransactionGroup = new TcTransactionGroup();
        tcTransactionGroup.setId(txGroupId);

        TcTransactionItem item = new TcTransactionItem();
        item.setTaskKey(taskKey);
        item.setStatus(status);
        item.setMessage(message);

        tcTransactionGroup.setItemList(Collections.singletonList(item));

        heartBeat.setTxTransactionGroup(tcTransactionGroup);
        nettyClientMessageHandler.asyncSendTxManagerMessage(heartBeat);
    }

    @Override
    public Boolean rollBackTxTransaction(String txGroupId) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.ROLLBACK.getCode());
        TcTransactionGroup tcTransactionGroup = new TcTransactionGroup();
        tcTransactionGroup.setStatus(TransactionStatusEnum.ROLLBACK.getCode());
        tcTransactionGroup.setId(txGroupId);
        heartBeat.setTxTransactionGroup(tcTransactionGroup);
        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Boolean) object;
        }
        return false;
    }

    @Override
    public Boolean addTxTransaction(String txGroupId, TcTransactionItem tcTransactionItem) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.ADD_TRANSACTION.getCode());
        TcTransactionGroup tcTransactionGroup = new TcTransactionGroup();
        tcTransactionGroup.setId(txGroupId);
        tcTransactionGroup.setItemList(Collections.singletonList(tcTransactionItem));
        heartBeat.setTxTransactionGroup(tcTransactionGroup);
        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Boolean) object;
        }
        return false;
    }

    @Override
    public int findTransactionGroupStatus(String tcGroupId) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.GET_TRANSACTION_GROUP_STATUS.getCode());
        TcTransactionGroup tcTransactionGroup = new TcTransactionGroup();
        tcTransactionGroup.setId(tcGroupId);

        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Integer) object;
        }
        return TransactionStatusEnum.ROLLBACK.getCode();
    }
}
