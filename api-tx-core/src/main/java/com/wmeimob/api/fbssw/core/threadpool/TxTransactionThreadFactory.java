package com.wmeimob.api.fbssw.core.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-08-11 16:51
 */
public class TxTransactionThreadFactory implements ThreadFactory {

    private static final Logger log = LoggerFactory.getLogger(TxTransactionThreadFactory.class);

    private final AtomicLong threadNumber = new AtomicLong(1);

    private String namePrefix;

    private static volatile boolean daemon;

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("txTransaction");

    private TxTransactionThreadFactory(String namePrefix, boolean daemon) {
        this.namePrefix = namePrefix;
        TxTransactionThreadFactory.daemon = daemon;
    }

    public static ThreadFactory create(String namePrefix, boolean daemon) {
        return new TxTransactionThreadFactory(namePrefix, daemon);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(THREAD_GROUP, r,
                THREAD_GROUP.getName() + "-" + namePrefix + "-" + threadNumber.getAndIncrement());
        thread.setDaemon(daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
