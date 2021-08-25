package com.wmeimob.api.fbssw.core.server;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-14 20:10
 */
@FunctionalInterface
public interface AspectTransactionService {

    /**
     * 切面方法
     * @param transactionGroupId 事务组ID
     * @param point 切点
     * @return
     * @throws Throwable
     */
    Object invoke(String transactionGroupId, ProceedingJoinPoint point) throws Throwable;
}
