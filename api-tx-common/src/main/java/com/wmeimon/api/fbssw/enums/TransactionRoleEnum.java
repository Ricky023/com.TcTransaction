package com.wmeimon.api.fbssw.enums;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:58
 */
public enum TransactionRoleEnum {

    /**
     * 发起者
     */
    START(0, "发起者"),


    /**
     * 参与者
     */
    ACTOR(1, "参与者"),

    /**
     * 事务组
     */
    GROUP(2,"事务组");

    private int code;

    private String desc;

    TransactionRoleEnum(int code, String desc) {
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
    }}
