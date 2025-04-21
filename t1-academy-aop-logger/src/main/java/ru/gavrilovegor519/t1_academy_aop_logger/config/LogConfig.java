package ru.gavrilovegor519.t1_academy_aop_logger.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.gavrilovegor519.t1_academy_aop_logger.aspects.LogAspects;
import ru.gavrilovegor519.t1_academy_aop_logger.props.LogProps;

@Configuration
@EnableConfigurationProperties(LogProps.class)
@EnableAspectJAutoProxy
@Slf4j
public class LogConfig {
    @Bean
    @ConditionalOnProperty(prefix = "log-aspects", name = "enable",
            havingValue = "true", matchIfMissing = true)
    public LogAspects logAspect(LogProps logProps) {
        log.atLevel(Level.valueOf(logProps.getAtLevel()))
                .log("LogAspects is enabled");
        return new LogAspects(logProps);
    }
}
