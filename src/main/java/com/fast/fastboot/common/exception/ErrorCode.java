package com.fast.fastboot.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    PERMISSION_DENIED(41001, "拒绝访问"),

    ORDER_NOT_EXIST(41000, "测试订单编号不存在");


    private int code;

    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
