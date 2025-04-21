package ru.gavrilovegor519.t1_academy_aop_logger.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.event.Level;
import ru.gavrilovegor519.t1_academy_aop_logger.props.LogProps;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class LogAspects {
    private final LogProps logProps;

    @AfterThrowing(value = "@annotation(ru.gavrilovegor519.t1_academy_aop_logger.aspects.LogAspects)", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        log.atLevel(Level.valueOf(logProps.getAtLevel()))
                .log("Exception thrown in method :{} with message: {}",
                        joinPoint.getSignature().getName(), throwable.getMessage());
    }

    @Around("@annotation(ru.gavrilovegor519.t1_academy_aop_logger.aspects.LogAspects)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.atLevel(Level.valueOf(logProps.getAtLevel()))
                .log("Method called: {}", proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        log.atLevel(Level.valueOf(logProps.getAtLevel()))
                .log("Method execution is finished: {}", proceedingJoinPoint.getSignature().getName());
        return result;
    }
}
