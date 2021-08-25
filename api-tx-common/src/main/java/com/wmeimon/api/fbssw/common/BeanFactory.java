package com.wmeimon.api.fbssw.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-21 23:31
 */
public class BeanFactory implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public BeanFactory() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext == null) {
            applicationContext = applicationContext;
        }

    }

    public static <T> T getBean(Class<T> typeClass) {
        return applicationContext.getBean(typeClass);
    }
}
