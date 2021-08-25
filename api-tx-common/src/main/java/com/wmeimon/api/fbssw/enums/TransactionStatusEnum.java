package com.wmeimon.api.fbssw.enums;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:54
 */
public enum TransactionStatusEnum {

    /**
     * 回滚
     */
    ROLLBACK(0, "回滚"),

    /**
     * 已经提交
     */
    COMMIT(1, "已经提交"),

    /**
     * 开始
     */
    BEGIN(2, "开始"),

    /**
     * 执行中
     */
    RUNNING(3, "执行中"),

    /**
     * 失败
     */
    FAILURE(4, "失败"),

    /**
     * 预提交
     */
    PRE_COMMIT(5, "预提交"),

    /**
     * 锁定
     */
    LOCK(6, "锁定");

    private int code;

    private String desc;

    TransactionStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
