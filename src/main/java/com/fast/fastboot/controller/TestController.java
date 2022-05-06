package com.fast.fastboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${config.testValue}")
    private String testValue;

    @GetMapping("/configTest")
    public String configTest() {
        return testValue;
    }
}
