package ru.gavrilovegor519.t1_academy_aop_logger.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gavrilovegor519.t1_academy_aop_logger.aspects.LogAspects;
import ru.gavrilovegor519.t1_academy_aop_logger.props.LogLevel;

@Configuration
@EnableConfigurationProperties(LogLevel.class)
@Log4j2
public class LogConfig {
    @Bean
    public LogAspects logAspect(LogLevel logLevel) {
        log.info("Log level: {}", logLevel.getLevel());
        return new LogAspects(logLevel);
    }
}
