package com.wmeimon.api.fbssw.holder;

import java.util.UUID;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:33
 */
public final class IdWorkerUtils {


    private static final IdWorkerUtils ID_WORKER_UTILS = new IdWorkerUtils();

    public static IdWorkerUtils getInstance() {
        return ID_WORKER_UTILS;
    }

    private IdWorkerUtils() {

    }

    public String createGroupId() {
        return String.valueOf(UUID.randomUUID().hashCode() & 0x7fffffff);
    }

    public String createTaskKey() {
        return String.valueOf(UUID.randomUUID().hashCode() & 0x7fffffff);
    }

    public String createUUID() {
        return String.valueOf(UUID.randomUUID().hashCode() & 0x7fffffff);
    }
}
