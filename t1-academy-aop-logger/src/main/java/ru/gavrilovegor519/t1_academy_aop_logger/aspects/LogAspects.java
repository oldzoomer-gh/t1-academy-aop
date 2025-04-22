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
        Level level = getLoggingLevel(logProps.getAtLevel());
        log.atLevel(level)
                .log("Exception thrown in method :{} with message: {}",
                        joinPoint.getSignature().getName(), throwable.getMessage());
    }

    @Around("@annotation(ru.gavrilovegor519.t1_academy_aop_logger.aspects.LogAspects)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Level level = getLoggingLevel(logProps.getAtLevel());
        log.atLevel(level)
                .log("Method called: {}", proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        log.atLevel(level)
                .log("Method execution is finished: {}", proceedingJoinPoint.getSignature().getName());
        return result;
    }

    private Level getLoggingLevel(String level) {
        try {
            return Level.valueOf(level.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid logging level: {}. Defaulting to INFO.", level);
            return Level.INFO;
        }
    }
}
