package com.wmeimon.api.fbssw.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-08-11 16:05
 */
public enum NettyResultEnum {

    /**
     * Begin transaction status enum.
     */
    SUCCESS(0, "成功"),


    /**
     * Fail netty result enum.
     */
    FAIL(1, "失败"),


    TIME_OUT(-1,"tmManager未连接或者响应超时！");


    private int code;

    private String desc;

    NettyResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static NettyResultEnum acquireByCode(int code) {
        Optional<NettyResultEnum> actionEnum =
                Arrays.stream(NettyResultEnum.values())
                        .filter(v -> Objects.equals(v.getCode(), code))
                        .findFirst();
        return actionEnum.orElse(NettyResultEnum.SUCCESS);

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
