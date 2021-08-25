package com.wmeimob.api.fbssw.core.concurrent.threadlocal;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-21 23:00
 */
public class CompensationLocal {

    private static final CompensationLocal COMPENSATION_LOCAL = new CompensationLocal();

    private CompensationLocal() {

    }

    public static CompensationLocal getInstance() {
        return COMPENSATION_LOCAL;
    }


    private static final ThreadLocal<String> CURRENT_LOCAL = new ThreadLocal<>();


    public void setCompensationId(String compensationId) {
        CURRENT_LOCAL.set(compensationId);
    }

    public String getCompensationId() {
        return CURRENT_LOCAL.get();
    }

    public void removeCompensationId() {
        CURRENT_LOCAL.remove();
    }
}
