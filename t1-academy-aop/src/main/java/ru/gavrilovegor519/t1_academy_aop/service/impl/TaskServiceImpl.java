package ru.gavrilovegor519.t1_academy_aop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution;
import ru.gavrilovegor519.t1_academy_aop.entity.Task;
import ru.gavrilovegor519.t1_academy_aop.exception.TaskNotFound;
import ru.gavrilovegor519.t1_academy_aop.repository.TaskRepository;
import ru.gavrilovegor519.t1_academy_aop.service.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    @LogExecution
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    @LogExecution
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFound::new);
    }

    @Override
    @LogExecution
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @LogExecution
    public Task updateTask(Long id, Task task) {
        Task oldTask = taskRepository.findById(id).orElseThrow();
        oldTask.setName(task.getName());
        oldTask.setDescription(task.getDescription());
        return taskRepository.save(oldTask);
    }

    @Override
    @LogExecution
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

}
