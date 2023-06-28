package com.ivoyant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class RequestResponseLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingAspect.class);

    @AfterReturning(pointcut = "@annotation(com.ivoyant.RequestResponseLogging)", returning = "result")
    public void logRequestResponse(JoinPoint joinPoint, Object result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        String parameters = Arrays.toString(joinPoint.getArgs());
        String response = (result != null) ? result.toString() : "null";

        logger.info("Request: {} {} | Parameters: {} | Response: {}", method, url, parameters, response);
    }
}
