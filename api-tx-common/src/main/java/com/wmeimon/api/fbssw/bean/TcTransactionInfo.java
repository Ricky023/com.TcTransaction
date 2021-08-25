package com.wmeimon.api.fbssw.bean;

import com.wmeimon.api.fbssw.enums.PropagationEnum;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-21 23:17
 */
public class TcTransactionInfo {

    public TcTransactionInfo(TransactionInvocation invocation, String tcGroupId, String compensationId,
                             int waitMaxTime, PropagationEnum propagationEnum) {
        this.invocation = invocation;
        this.tcGroupId = tcGroupId;
        this.compensationId = compensationId;
        this.waitMaxTime = waitMaxTime;
        this.propagationEnum = propagationEnum;
    }

    public TcTransactionInfo(){}

    /**
     * 补偿方法对象
     */
    private TransactionInvocation invocation;

    /**
     * 分布式事务组
     */
    private String tcGroupId;

    /**
     * 事务补偿id
     */
    private String compensationId;

    /**
     * 事务等待时间
     */
    private int waitMaxTime = 60;

    private PropagationEnum propagationEnum;

    public TransactionInvocation getInvocation() {
        return invocation;
    }

    public void setInvocation(TransactionInvocation invocation) {
        this.invocation = invocation;
    }

    public String getTcGroupId() {
        return tcGroupId;
    }

    public void setTcGroupId(String tcGroupId) {
        this.tcGroupId = tcGroupId;
    }

    public String getCompensationId() {
        return compensationId;
    }

    public void setCompensationId(String compensationId) {
        this.compensationId = compensationId;
    }

    public int getWaitMaxTime() {
        return waitMaxTime;
    }

    public void setWaitMaxTime(int waitMaxTime) {
        this.waitMaxTime = waitMaxTime;
    }

    public PropagationEnum getPropagationEnum() {
        return propagationEnum;
    }

    public void setPropagationEnum(PropagationEnum propagationEnum) {
        this.propagationEnum = propagationEnum;
    }
}
