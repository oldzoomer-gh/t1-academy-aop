package ru.gavrilovegor519.t1_academy_aop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.gavrilovegor519.t1_academy_aop.dto.MailLetter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationServiceImpl(mailSender, "test@example.com");
    }

    @Test
    void testSendEmail() {
        MailLetter mailLetter = new MailLetter();
        mailLetter.setEmail("recipient@example.com");
        mailLetter.setSubject("Test Subject");
        mailLetter.setText("Test Text");

        notificationService.sendEmail(mailLetter);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
