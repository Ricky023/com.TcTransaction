package com.wmeimon.api.fbssw.enums;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:19
 */
public enum CompensationActionEnum {

    /**
     * Save compensate action enum.
     */
    SAVE(0,"保存"),

    /**
     * Delete compensate action enum.
     */
    DELETE(1,"删除"),

    /**
     * Update compensate action enum.
     */
    UPDATE(2,"更新"),

    /**
     * Compensate compensate action enum.
     */
    COMPENSATE(3,"补偿");

    private int code;

    private String desc;

    CompensationActionEnum(int code,String desc){
        this.code=code;
        this.desc=desc;
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
