package com.wmeimob.api.fbssw.core.server;

import com.wmeimon.api.fbssw.netty.TcTransactionGroup;
import com.wmeimon.api.fbssw.netty.TcTransactionItem;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:14
 */
public interface TcManagerMessageService {

    /**
     * 保存事务组 在事务发起方的时候进行调用
     * @param tcTransactionGroup
     * @return
     */
    Boolean saveTcTransactionGroup(TcTransactionGroup tcTransactionGroup);

    /**
     * 通知tm自身业务已经执行完成，等待提交事务
     * tm 收到后，进行pre_commit  再进行doCommit
     *
     * @param tcGroupId 事务组id
     * @return true 成功 false 失败
     */
    Boolean preCommitTxTransaction(String tcGroupId);

    /**
     * 异步完成自身的提交
     *
     * @param txGroupId 事务组id
     * @param taskKey   子事务的taskKey
     * @param status    状态
     * @param message   完成信息 返回结果，或者是异常信息
     */
    void asyncCompleteCommit(String txGroupId, String taskKey, int status, Object message);

    /**
     * 通知tm 回滚整个事务组
     *
     * @param txGroupId 事务组id
     * @return true 成功 false 失败
     */
    Boolean rollBackTxTransaction(String txGroupId);

    /**
     * 往事务组添加事务
     *
     * @param txGroupId         事务组id
     * @param tcTransactionItem 子事务项
     * @return true 成功 false 失败
     */
    Boolean addTxTransaction(String txGroupId, TcTransactionItem tcTransactionItem);

    /**
     * 获取事务组状态
     *
     * @param tcGroupId 事务组id
     * @return 事务组状态
     */
    int findTransactionGroupStatus(String tcGroupId);
}