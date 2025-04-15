package ru.gavrilovegor519.t1_academy_aop_logger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.gavrilovegor519.t1_academy_aop_logger.aspects.LogAspects;

public class LogAspectsConfig {
    @Bean
    @ConfigurationProperties(prefix = "log-aspects")
    public LogAspects logAspects() {
        return new LogAspects();
    }
}
