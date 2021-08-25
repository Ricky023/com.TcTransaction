package com.wmeimob.api.fbssw.core.server;

import com.wmeimon.api.fbssw.bean.TcTransactionInfo;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-21 23:25
 */
public interface TcTransactionFactoryService<T> {

    /**
     * 返回 实现TxTransactionHandler类的名称
     * @param info
     * @author Ricky Li
     * @return
     * @throws Throwable
     */
    Class<T> factoryOf(TcTransactionInfo info) throws Throwable;
}
