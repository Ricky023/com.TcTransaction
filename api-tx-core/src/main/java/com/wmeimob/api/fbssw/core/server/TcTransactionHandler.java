package com.wmeimob.api.fbssw.core.server;

import com.wmeimon.api.fbssw.bean.TcTransactionInfo;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-21 23:27
 */
@FunctionalInterface
public interface TcTransactionHandler {

    Object handler(ProceedingJoinPoint point, TcTransactionInfo info) throws Throwable;
}
