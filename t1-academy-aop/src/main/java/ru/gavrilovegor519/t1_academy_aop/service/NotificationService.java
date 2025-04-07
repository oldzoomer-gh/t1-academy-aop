package ru.gavrilovegor519.t1_academy_aop.service;

import ru.gavrilovegor519.t1_academy_aop.dto.MailLetter;

public interface NotificationService {
    void sendEmail(MailLetter mailLetter);
}
