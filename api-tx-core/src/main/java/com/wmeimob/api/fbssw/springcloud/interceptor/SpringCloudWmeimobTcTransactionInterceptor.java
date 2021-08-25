package com.wmeimob.api.fbssw.springcloud.interceptor;

import com.wmeimob.api.fbssw.core.concurrent.threadlocal.CompensationLocal;
import com.wmeimob.api.fbssw.core.interceptor.WmeimobTcTransactionInterceptor;
import com.wmeimob.api.fbssw.core.server.AspectTransactionService;
import com.wmeimon.api.fbssw.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-07 22:42
 */
@Component
public class SpringCloudWmeimobTcTransactionInterceptor implements WmeimobTcTransactionInterceptor {

    @Autowired
    private AspectTransactionService aspectTransactionService;

    @Override
    public Object interceptor(ProceedingJoinPoint point) throws Throwable {

        try {
            final String compensationId = CompensationLocal.getInstance().getCompensationId();
            String groupId = null;
            if (StringUtils.isEmpty(compensationId)) {
                //如果不是本地反射调用补偿
                RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
                HttpServletRequest request = requestAttributes == null ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
                groupId = request == null ? null : request.getHeader(CommonConstant.TX_TRANSACTION_GROUP);
            }

            return aspectTransactionService.invoke(groupId, point);
        } catch (Throwable throwable) {

            return  point.proceed();
        }
    }
}
