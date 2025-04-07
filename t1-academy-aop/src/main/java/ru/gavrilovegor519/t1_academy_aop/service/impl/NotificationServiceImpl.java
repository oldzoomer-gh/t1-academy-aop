package ru.gavrilovegor519.t1_academy_aop.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution;
import ru.gavrilovegor519.t1_academy_aop.dto.MailLetter;
import ru.gavrilovegor519.t1_academy_aop.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender mailSender;
    private final String from;

    public NotificationServiceImpl(JavaMailSender mailSender, 
                                    @Value("${spring.mail.from}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    @LogExecution
    public void sendEmail(MailLetter mailLetter) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mailLetter.getEmail());
        message.setSubject(mailLetter.getSubject());
        message.setText(mailLetter.getText());
        mailSender.send(message);
    }

}
