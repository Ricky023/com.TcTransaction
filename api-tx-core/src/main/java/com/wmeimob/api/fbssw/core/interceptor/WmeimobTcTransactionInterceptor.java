package com.wmeimob.api.fbssw.core.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-07 22:13
 */
@FunctionalInterface
public interface WmeimobTcTransactionInterceptor {

    /**
     * 事务切面拦截
     * @param point
     * @return
     * @throws Throwable
     */
    Object interceptor(ProceedingJoinPoint point) throws  Throwable;
}
