package com.wmeimon.api.fbssw.config;

import org.springframework.stereotype.Component;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:29
 */
@Component
public class TxConfig {

    /**
     * 延迟时间
     */
    private int delayTime = 30;

    /**
     * 执行事务的线程数大小
     */
    private int transactionThreadMax = Runtime.getRuntime().availableProcessors() << 1;

    /**
     * 线程池的拒绝策略
     */
    private String rejectPolicy = "Abort";

    /**
     * 线程池的队列类型
     */
    private String blockingQueueType = "Linked";

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public int getTransactionThreadMax() {
        return transactionThreadMax;
    }

    public void setTransactionThreadMax(int transactionThreadMax) {
        this.transactionThreadMax = transactionThreadMax;
    }

    public String getRejectPolicy() {
        return rejectPolicy;
    }

    public void setRejectPolicy(String rejectPolicy) {
        this.rejectPolicy = rejectPolicy;
    }

    public String getBlockingQueueType() {
        return blockingQueueType;
    }

    public void setBlockingQueueType(String blockingQueueType) {
        this.blockingQueueType = blockingQueueType;
    }
}
