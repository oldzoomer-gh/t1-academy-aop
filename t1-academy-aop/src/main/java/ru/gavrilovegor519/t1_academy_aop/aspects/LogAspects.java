package ru.gavrilovegor519.t1_academy_aop.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LogAspects {
    @AfterThrowing(value = "@annotation(ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution)", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        log.error("Exception thrown in method :{} with message: {}", joinPoint.getSignature().getName(), throwable.getMessage());
    }

    @Around("@annotation(ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Method called: {}", proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        log.info("Method execution is finished: {}", proceedingJoinPoint.getSignature().getName());
        return result;
    }
}
