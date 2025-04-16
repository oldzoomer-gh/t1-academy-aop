package ru.gavrilovegor519.t1_academy_aop_logger.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.gavrilovegor519.t1_academy_aop_logger.enums.LogLevelEnum;

@Setter
@Getter
@ConfigurationProperties(prefix = "log-aspects")
public class LogLevel {
    private LogLevelEnum level;

    public boolean isInfo() {
        if (level == null) return false;
        return level == LogLevelEnum.INFO ||
                level == LogLevelEnum.DEBUG;
    }
}
