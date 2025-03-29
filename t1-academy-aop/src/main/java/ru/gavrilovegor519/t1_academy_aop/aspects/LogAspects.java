package ru.gavrilovegor519.t1_academy_aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Log4j2
public class LogAspects {
    @Pointcut("within(ru.gavrilovegor519.t1_academy_aop.service.impl..*)")
    public void allServiceMethods() {
        
    }

    @Before("@annotation(ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution)")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Method called successfully: " + joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "@annotation(ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution)", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        log.info("Exception thrown in method :" + joinPoint.getSignature().getName() + " with message: " + throwable.getMessage());
    }

    @AfterReturning("@annotation(ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution)")
    public void logAfterReturning(JoinPoint joinPoint) {
        log.info("Method returned: " + joinPoint.getSignature().getName());
    }

    @Around("@annotation(ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Method called: " + proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        log.info("Method execution is finished: " + proceedingJoinPoint.getSignature().getName());
        return result;
    }
}
