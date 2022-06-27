package com.fast.fastboot.controller;

import com.fast.fastboot.common.exception.BusinessException;
import com.fast.fastboot.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${config.testValue}")
    private String testValue;

    @GetMapping("/configTest")
    public String configTest() {
        return testValue + "123456790";
    }

    @GetMapping("/errorTest")
    public String errorTest() {
        throw new BusinessException(ErrorCode.PERMISSION_DENIED);
    }
}
