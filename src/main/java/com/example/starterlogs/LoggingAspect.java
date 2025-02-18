package com.example.starterlogs;

import jakarta.annotation.PostConstruct;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Aspect
@ComponentScan("com.example.starterlogs")
@ConditionalOnProperty(name = "starter-logs.enabled", havingValue = "true", matchIfMissing = true)
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @PostConstruct
    public void init() {
        System.out.println("LOGGABLE Aspect Initialized");
    }
    @Around("@annotation(com.example.starterlogs.Loggable)")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();

        logger.info("Executing method: {}", methodName);
        Object result = joinPoint.proceed();
        logger.info("Completed method: {}", methodName);

        return result;
    }
}
