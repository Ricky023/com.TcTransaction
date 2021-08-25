package com.wmeimob.api.fbssw.core.server.handler;

import com.wmeimob.api.fbssw.core.server.TcTransactionHandler;
import com.wmeimon.api.fbssw.bean.TcTransactionInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-08-11 16:41
 */
public class InsideCompensationHandler implements TcTransactionHandler {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    public Object handler(ProceedingJoinPoint point, TcTransactionInfo info) throws Throwable {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(def);
        try {
            return point.proceed();
        } finally {
            platformTransactionManager.rollback(transactionStatus);
        }
    }
}
