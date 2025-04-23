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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private static final String FROM_EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        notificationService = new NotificationServiceImpl(mailSender, FROM_EMAIL);
    }

    @Test
    void testSendEmail() {
        MailLetter mailLetter = mock(MailLetter.class);
        notificationService.sendEmail(mailLetter);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
