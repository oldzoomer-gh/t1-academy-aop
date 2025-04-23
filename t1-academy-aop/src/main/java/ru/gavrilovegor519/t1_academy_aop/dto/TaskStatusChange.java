package ru.gavrilovegor519.t1_academy_aop.dto;

import lombok.Getter;
import lombok.Setter;
import ru.gavrilovegor519.t1_academy_aop.enums.TaskStatus;

@Getter
@Setter
public class TaskStatusChange {
    private Long id;
    private TaskStatus taskStatus;
    private String email;
}
