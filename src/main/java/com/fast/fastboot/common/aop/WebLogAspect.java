package com.fast.fastboot.common.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
        } catch (Throwable e) {
            success = false;
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            log.info("complete request, uri: {}, success: {}, responseTime: {}", uri, success, end - start);
        }
    }

}