package com.wmeimob.api.fbssw.core.compensation;

import com.wmeimob.api.fbssw.core.compensation.command.TcCompensationAction;
import com.wmeimon.api.fbssw.bean.TransactionRecover;
import com.wmeimon.api.fbssw.config.TxConfig;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:23
 */
public interface TcCompensationService {

    /**
     * 补偿操作
     */
    void compensate();


    /**
     * 启动本地补偿事务，根据配置是否进行补偿
     *
     * @param txConfig 配置信息
     * @throws Exception 异常信息
     */
    void start(TxConfig txConfig) throws Exception;

    /**
     * 保存补偿事务信息
     *
     * @param transactionRecover 实体对象
     * @return 主键id
     */
    String save(TransactionRecover transactionRecover);


    /**
     * 删除补偿事务信息
     *
     * @param id 主键id
     * @return true成功 false 失败
     */
    boolean remove(String id);


    /**
     * 更新
     *
     * @param transactionRecover 实体对象
     */
    void update(TransactionRecover transactionRecover);


    /**
     * 提交补偿
     *
     * @param tcCompensationAction 补偿命令
     * @return true 成功
     */
    Boolean submit(TcCompensationAction tcCompensationAction);
}
