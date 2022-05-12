package com.fast.fastboot.common.bean;

import com.fast.fastboot.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResult<T> implements Serializable {

    private T data;

    private int code;

    private String message;

    private static final int SUCCESS_CODE = 1;
    private static final int SYSTEM_ERROR_CODE = 500;


    public static <T> RestResult<T> success(T data) {
        return new RestResult<T>(data, SUCCESS_CODE, null);
    }

    public static RestResult<Object> error(BusinessException businessException) {
        return new RestResult<>(null,
                businessException.getErrorCode().getCode(),
                businessException.getErrorCode().getMessage());
    }

    public static RestResult<Object> systemError(String message) {
        return new RestResult<>(null, SYSTEM_ERROR_CODE, message);
    }

}




