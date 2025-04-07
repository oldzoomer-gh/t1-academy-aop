package ru.gavrilovegor519.t1_academy_aop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.gavrilovegor519.t1_academy_aop.enums.TaskStatus;

@Getter
@Setter
@Entity(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "author_email")
    private String authorEmail;

    @Column(name = "executor_email")
    private String executorEmail;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
}
