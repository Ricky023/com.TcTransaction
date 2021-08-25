package com.wmeimob.api.fbssw.core.concurrent.task;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-08-11 16:26
 */
@FunctionalInterface
public interface AsyncCall {

    /**
     * 回调处理
     *
     * @param objects 参数
     * @return Object
     * @throws Throwable 异常
     */
    Object callBack(Object... objects) throws Throwable;
}
