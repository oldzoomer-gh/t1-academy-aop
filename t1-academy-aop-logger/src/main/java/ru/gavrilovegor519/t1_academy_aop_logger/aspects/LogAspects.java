package ru.gavrilovegor519.t1_academy_aop_logger.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.gavrilovegor519.t1_academy_aop_logger.props.LogLevel;

@Aspect
@Log4j2
@RequiredArgsConstructor
public class LogAspects {
    private final LogLevel logLevel;

    @Pointcut("@annotation(ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution)")
    public void logExecution() {
    }

    @AfterThrowing(pointcut = "logExecution()", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        log.error("Exception thrown in method :{} with message: {}", joinPoint.getSignature().getName(), throwable.getMessage());
    }

    @Around("logExecution()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (logLevel.isInfo()) log.info("Method called: {}", proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        if (logLevel.isInfo())
            log.info("Method execution is finished: {}", proceedingJoinPoint.getSignature().getName());
        return result;
    }
}
