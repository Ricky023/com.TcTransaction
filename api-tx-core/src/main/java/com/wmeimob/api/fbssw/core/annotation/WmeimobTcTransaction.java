package com.wmeimob.api.fbssw.core.annotation;

import com.wmeimon.api.fbssw.enums.PropagationEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-07 21:29
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WmeimobTcTransaction {

    PropagationEnum propagation() default PropagationEnum.PROPAGATION_REQUIRES_NEW;

    /**
     * 设置事务的最大等待时间（秒）
     * @return
     */
    int waitMaxTime() default 60;

}
