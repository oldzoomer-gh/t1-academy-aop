package ru.gavrilovegor519.t1_academy_aop.service;

import java.util.List;

import ru.gavrilovegor519.t1_academy_aop.entity.Task;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
}
