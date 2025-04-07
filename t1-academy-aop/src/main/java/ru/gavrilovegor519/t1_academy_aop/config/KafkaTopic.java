package ru.gavrilovegor519.t1_academy_aop.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    @Value("${spring.kafka.topic}")
    private String kafkaTopic;

    @Bean
    NewTopic sendEmailAboutTasks() {
        return TopicBuilder.name(kafkaTopic)
                .partitions(1)
                .build();
    }
}