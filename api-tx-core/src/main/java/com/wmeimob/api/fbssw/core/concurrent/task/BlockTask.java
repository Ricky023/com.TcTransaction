package com.wmeimob.api.fbssw.core.concurrent.task;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-08-11 16:11
 */
public class BlockTask {

    private Lock lock;
    private Condition condition;

    private AsyncCall asyncCall;

    /**
     * 是否被唤醒
     */
    private volatile static boolean Notify = false;

    /**
     * 是否被唤醒
     */
    private volatile static boolean remove = false;

    /**
     * 唯一标示key
     */
    private String key;

    /**
     * 数据状态用于业务处理
     */
    private int state = 0;

    public BlockTask() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void signal() {
        lock.lock();
        try {
            Notify = true;
            condition.signal();
        } finally {
            lock.unlock();
        }

    }


    public void await() {
        lock.lock();
        try {
            condition.await();
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public AsyncCall getAsyncCall() {
        return asyncCall;
    }

    public void setAsyncCall(AsyncCall asyncCall) {
        this.asyncCall = asyncCall;
    }

    public static boolean isNotify() {
        return Notify;
    }

    public static void setNotify(boolean notify) {
        Notify = notify;
    }

    public static boolean isRemove() {
        return remove;
    }

    public static void setRemove(boolean remove) {
        BlockTask.remove = remove;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
