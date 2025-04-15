package ru.gavrilovegor519.t1_academy_aop.utils;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.gavrilovegor519.t1_academy_aop.dto.MailLetter;
import ru.gavrilovegor519.t1_academy_aop.dto.TaskStatusChange;
import ru.gavrilovegor519.t1_academy_aop.service.NotificationService;
import ru.gavrilovegor519.t1_academy_aop_logger.annotations.LogExecution;

@Component
@AllArgsConstructor
public class TaskUpdateKafkaListener {
    private final NotificationService notificationService;

    @KafkaListener(topicPattern = "${spring.kafka.topic}", groupId = "t1-academy-aop")
    @LogExecution
    public void sendEmailAboutTaskUpdate(TaskStatusChange taskStatusChange) {
        MailLetter mailLetter = new MailLetter();
        mailLetter.setEmail(taskStatusChange.getEmail());
        mailLetter.setSubject("Task status changed");
        mailLetter.setText("Task with id " + taskStatusChange.getId() + " has new status " + taskStatusChange.getTaskStatus());
        notificationService.sendEmail(mailLetter);
    }
}
