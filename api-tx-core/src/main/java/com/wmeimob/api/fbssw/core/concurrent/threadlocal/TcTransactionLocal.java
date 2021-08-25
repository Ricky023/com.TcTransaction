package com.wmeimob.api.fbssw.core.concurrent.threadlocal;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-21 23:49
 */
public class TcTransactionLocal {

    private static final TcTransactionLocal TC_TRANSACTION_LOCAL = new TcTransactionLocal();

    private TcTransactionLocal() {

    }

    public static TcTransactionLocal getInstance() {
        return TC_TRANSACTION_LOCAL;
    }


    private static final ThreadLocal<String> CURRENT_LOCAL = new ThreadLocal<>();


    public void setTcGroupId(String txGroupId) {
        CURRENT_LOCAL.set(txGroupId);
    }

    public String getTcGroupId() {
        return CURRENT_LOCAL.get();
    }

    public void removeTcGroupId() {
        CURRENT_LOCAL.remove();
    }
}
