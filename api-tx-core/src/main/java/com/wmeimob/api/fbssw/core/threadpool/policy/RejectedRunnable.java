package com.wmeimob.api.fbssw.core.threadpool.policy;

public interface RejectedRunnable extends Runnable {

    /**
     * 线程池拒绝策略
     */
    void rejected();
}
