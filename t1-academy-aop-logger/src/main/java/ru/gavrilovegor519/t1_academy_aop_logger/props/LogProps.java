package ru.gavrilovegor519.t1_academy_aop_logger.props;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "log-aspects")
@AllArgsConstructor
@Getter
public class LogProps {
    private boolean enable;
    private String atLevel;
}
