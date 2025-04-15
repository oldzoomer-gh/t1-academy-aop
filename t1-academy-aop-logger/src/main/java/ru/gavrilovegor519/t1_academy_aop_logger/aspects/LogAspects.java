package ru.gavrilovegor519.t1_academy_aop_logger.aspects;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Log4j2
@Setter
public class LogAspects {
    private String level;

    @AfterThrowing(value = "@annotation(ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution)", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        log.error("Exception thrown in method :{} with message: {}", joinPoint.getSignature().getName(), throwable.getMessage());
    }

    @Around("@annotation(ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (level.equals("info")) log.info("Method called: {}", proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        if (level.equals("info"))
            log.info("Method execution is finished: {}", proceedingJoinPoint.getSignature().getName());
        return result;
    }
}
