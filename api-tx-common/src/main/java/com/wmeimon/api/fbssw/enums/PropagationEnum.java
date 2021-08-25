package com.wmeimon.api.fbssw.enums;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-07 21:37
 */
public enum PropagationEnum {

    /**
     * 如果存在一个事务，则支持当前事务。如果没有事务则开启
     */
    PROPAGATION_REQUIRED(0),

    /**
     * 如果存在一个事务，支持当前事务。如果没有事务，则非事务的执行
     */
    PROPAGATION_SUPPORTS(1),

    /**
     * 如果已经存在一个事务，支持当前事务。如果没有一个活动的事务，则抛出异常
     */
    PROPAGATION_MANDATORY(2),

    /**
     * 总是开启一个新的事务。如果一个事务已经存在，则将这个存在的事务挂起
     */
    PROPAGATION_REQUIRES_NEW(3),

    /**
     * 总是非事务地执行，并挂起任何存在的事务
     */
    PROPAGATION_NOT_SUPPORTED(4),

    /**
     * 总是非事务地执行，如果存在一个活动事务，则抛出异常
     */
    PROPAGATION_NEVER(5),

    /**
     * 如果一个活动的事务存在，则运行在一个嵌套的事务中. 如果没有活动事务,
     * 则按PROPAGATION_REQUIRED 属性执行
     */
    PROPAGATION_NESTED(6);


    private final int value;

    PropagationEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }


}
