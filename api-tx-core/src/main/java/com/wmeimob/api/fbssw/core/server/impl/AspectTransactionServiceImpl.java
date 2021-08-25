package com.wmeimob.api.fbssw.core.server.impl;

import com.wmeimob.api.fbssw.core.annotation.WmeimobTcTransaction;
import com.wmeimob.api.fbssw.core.concurrent.threadlocal.CompensationLocal;
import com.wmeimob.api.fbssw.core.server.AspectTransactionService;
import com.wmeimob.api.fbssw.core.server.TcTransactionFactoryService;
import com.wmeimob.api.fbssw.core.server.TcTransactionHandler;
import com.wmeimon.api.fbssw.bean.TcTransactionInfo;
import com.wmeimon.api.fbssw.bean.TransactionInvocation;
import com.wmeimon.api.fbssw.common.BeanFactory;
import com.wmeimon.api.fbssw.enums.PropagationEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-21 23:13
 */
public class AspectTransactionServiceImpl implements AspectTransactionService {

    @Autowired
    private TcTransactionFactoryService tcTransactionFactoryService;

    @Override
    public Object invoke(String transactionGroupId, ProceedingJoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getMethod(signature.getName(),signature.getParameterTypes());

        Class<?> clazz = point.getTarget().getClass();
        Object[] args = point.getArgs();
        Method thisMethod = clazz.getMethod(method.getName(), method.getParameterTypes());

        final String compensationId = CompensationLocal.getInstance().getCompensationId();

        final WmeimobTcTransaction wmeimobTcTransaction = method.getAnnotation(WmeimobTcTransaction.class);

        final int waitMaxTime = wmeimobTcTransaction.waitMaxTime();

        final PropagationEnum propagation = wmeimobTcTransaction.propagation();

        TransactionInvocation invocation = new TransactionInvocation(clazz, thisMethod.getName(), args, method.getParameterTypes());
        TcTransactionInfo info = new TcTransactionInfo(invocation,transactionGroupId,compensationId,waitMaxTime,propagation);
        final Class c = tcTransactionFactoryService.factoryOf(info);
        final TcTransactionHandler tcTransactionHandler = (TcTransactionHandler) BeanFactory.getBean(c);
        return tcTransactionHandler.handler(point, info);

    }
}
