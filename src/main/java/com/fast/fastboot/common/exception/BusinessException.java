package com.fast.fastboot.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getCode() + ": " + errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
