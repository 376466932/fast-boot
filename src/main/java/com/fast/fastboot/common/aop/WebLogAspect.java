package com.fast.fastboot.common.aop;

import com.alibaba.fastjson.JSONObject;
import com.fast.fastboot.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class WebLogAspect {


    @Pointcut("execution(* com.fast.fastboot.controller..*.*(..)))")
    public void controller() {
    }
    @Pointcut("execution(* com.fast.fastboot.controller.common.ExceptionHandleController.*(..))")
    public void exclude() {
    }
    @Pointcut("controller() && !exclude()")
    public void aspectLog() {
    }

    @Around("aspectLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //get request and response
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        //generate request id
        String xRequestId = request.getHeader(Constants.X_REQUEST_ID_HEADER_NAME);
        if (! StringUtils.hasText(xRequestId)) {
            xRequestId = UUID.randomUUID().toString().replaceAll("-", "");
        }
        //set the request id to response and MDC
        response.setHeader(Constants.X_REQUEST_ID_HEADER_NAME, xRequestId);
        MDC.put(Constants.X_REQUEST_ID_HEADER_NAME, xRequestId);

        String uri = request.getRequestURI();
        Object[] args = pjp.getArgs();
        long start = System.currentTimeMillis();
        String param;
        if ("POST".equals(request.getMethod())) {
            param = JSONObject.toJSONString(args);
        } else {
            param = request.getQueryString();
        }
        log.info("receive request, uri: {}, param: {}", uri, param);
        boolean success = true;
        try {
            return pjp.proceed();
        } catch (Exception e) {
            success = false;
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            log.info("complete request, uri: {}, success: {}, response time: {}", uri, success, end - start);
        }
    }

    @After("aspectLog()")
    public void afterAdvice() {
        MDC.remove(Constants.X_REQUEST_ID_HEADER_NAME);
    }


}