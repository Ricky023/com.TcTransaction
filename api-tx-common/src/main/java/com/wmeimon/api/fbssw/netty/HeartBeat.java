package com.wmeimon.api.fbssw.netty;

import java.io.Serializable;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-08-11 15:55
 */
public class HeartBeat implements Serializable {

    private static final long serialVersionUID = 4183978848464761529L;

    /**
     * 执行动作
     */
    private int action;

    /**
     * 执行发送数据任务task key
     */
    private String key;

    private int result;


    /**
     * 事务组信息
     */
    private TcTransactionGroup txTransactionGroup;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public TcTransactionGroup getTxTransactionGroup() {
        return txTransactionGroup;
    }

    public void setTxTransactionGroup(TcTransactionGroup txTransactionGroup) {
        this.txTransactionGroup = txTransactionGroup;
    }
}
