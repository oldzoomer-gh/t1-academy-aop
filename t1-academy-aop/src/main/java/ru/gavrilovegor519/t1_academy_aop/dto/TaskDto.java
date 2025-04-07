package ru.gavrilovegor519.t1_academy_aop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import ru.gavrilovegor519.t1_academy_aop.enums.TaskStatus;

/**
 * DTO for {@link ru.gavrilovegor519.t1_academy_aop.entity.Task}
 * 
 * JSON example:
 * {
 *     "authorEmail": "author@mail.ru",
 *     "executorEmail": "executor@mail.ru",
 *     "name": "Task name",
 *     "description": "Task description",
 *     "taskStatus": "NEW"
 * }
 *
 */
@Getter
@Setter
public class TaskDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String authorEmail;
    private String executorEmail;
    private String name;
    private String description;
    private TaskStatus taskStatus;
}