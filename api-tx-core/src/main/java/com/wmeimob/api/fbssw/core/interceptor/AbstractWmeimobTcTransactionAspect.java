package com.wmeimob.api.fbssw.core.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-07 21:57
 */
@Aspect
public abstract class AbstractWmeimobTcTransactionAspect {

    @Autowired
    WmeimobTcTransactionInterceptor wmeimobTcTransactionInterceptor;

    public void setWmeimobTcTransactionInterceptor(WmeimobTcTransactionInterceptor wmeimobTcTransactionInterceptor) {
        this.wmeimobTcTransactionInterceptor = wmeimobTcTransactionInterceptor;
    }

    @Pointcut("@annotation(com.wmeimob.api.fbssw.core.annotation.WmeimobTcTransaction)")
    public void wmeimobTcTransactionInterceptor() {

    }

    @Around("wmeimobTcTransactionInterceptor()")
    public Object interceptCompensableMethod(ProceedingJoinPoint point) throws Throwable {
        return wmeimobTcTransactionInterceptor.interceptor(point);
    }
}
