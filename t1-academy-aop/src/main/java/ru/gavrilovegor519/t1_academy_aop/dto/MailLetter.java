package ru.gavrilovegor519.t1_academy_aop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailLetter {
    private String email;
    private String subject;
    private String text;
}
