package ru.gavrilovegor519.t1_academy_aop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.gavrilovegor519.t1_academy_aop.annotations.LogExecution;
import ru.gavrilovegor519.t1_academy_aop.entity.Task;
import ru.gavrilovegor519.t1_academy_aop.repository.TaskRepository;
import ru.gavrilovegor519.t1_academy_aop.service.TaskService;

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
        return taskRepository.findById(id).orElseThrow();
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
