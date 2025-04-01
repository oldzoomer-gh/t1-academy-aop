package ru.gavrilovegor519.t1_academy_aop.dto;

import lombok.Value;

/**
 * DTO for {@link ru.gavrilovegor519.t1_academy_aop.entity.Task}
 */
@Value
public class TaskDto {
    String name;
    String description;
}