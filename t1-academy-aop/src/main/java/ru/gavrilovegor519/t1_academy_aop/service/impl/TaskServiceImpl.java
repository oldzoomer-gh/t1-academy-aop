package ru.gavrilovegor519.t1_academy_aop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gavrilovegor519.t1_academy_aop.dto.TaskStatusChange;
import ru.gavrilovegor519.t1_academy_aop.entity.Task;
import ru.gavrilovegor519.t1_academy_aop.exception.TaskNotFound;
import ru.gavrilovegor519.t1_academy_aop.repository.TaskRepository;
import ru.gavrilovegor519.t1_academy_aop.service.TaskService;
import ru.gavrilovegor519.t1_academy_aop_logger.annotations.LogExecution;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final KafkaTemplate<String, TaskStatusChange> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String kafkaTopic;

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
        boolean statusIsChanged = false;

        Task oldTask = taskRepository.findById(id).orElseThrow(TaskNotFound::new);

        if (task.getName() != null) oldTask.setName(task.getName());
        if (task.getDescription() != null) oldTask.setDescription(task.getDescription());

        if (!oldTask.getTaskStatus().equals(task.getTaskStatus()) && task.getTaskStatus() != null) {
            oldTask.setTaskStatus(task.getTaskStatus());
            statusIsChanged = true;
        }

        Task newTask = taskRepository.save(oldTask);

        if (statusIsChanged) {
            TaskStatusChange taskStatusChange = new TaskStatusChange();
            taskStatusChange.setId(newTask.getId());
            taskStatusChange.setEmail(newTask.getExecutorEmail());
            taskStatusChange.setTaskStatus(newTask.getTaskStatus());
            kafkaTemplate.send(kafkaTopic, taskStatusChange);
        }

        return newTask;
    }

    @Override
    @LogExecution
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
