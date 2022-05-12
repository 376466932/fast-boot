package com.fast.fastboot.controller.common;

import com.fast.fastboot.common.bean.RestResult;
import com.fast.fastboot.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class ExceptionHandleController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestResult<Object> errorHandler(HttpServletRequest request,
                                           HttpServletResponse response, Exception e)throws Exception {

        return RestResult.systemError(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RestResult<Object> errorHandler(HttpServletRequest request,
                                           HttpServletResponse response, BusinessException e){

        return RestResult.error(e);
    }

}
