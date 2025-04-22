package ru.gavrilovegor519.t1_academy_aop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import ru.gavrilovegor519.t1_academy_aop.dto.TaskStatusChange;
import ru.gavrilovegor519.t1_academy_aop.entity.Task;
import ru.gavrilovegor519.t1_academy_aop.enums.TaskStatus;
import ru.gavrilovegor519.t1_academy_aop.repository.TaskRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private KafkaTemplate<String, TaskStatusChange> kafkaTemplate;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskRepository, kafkaTemplate);
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new Task();
        Task task2 = new Task();
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        verify(taskRepository).findAll();
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);

        assertNotNull(foundTask);
        verify(taskRepository).findById(1L);
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        verify(taskRepository).save(task);
    }

    @Test
    void testUpdateTask() {
        Task oldTask = new Task();
        oldTask.setId(1L);
        oldTask.setTaskStatus(TaskStatus.NEW);
        oldTask.setExecutorEmail("executor@example.com");

        Task newTask = new Task();
        newTask.setTaskStatus(TaskStatus.CANCELED);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(oldTask));
        when(taskRepository.save(any(Task.class))).thenReturn(oldTask);

        Task updatedTask = taskService.updateTask(1L, newTask);

        assertEquals(TaskStatus.CANCELED, updatedTask.getTaskStatus());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(oldTask);
        verify(kafkaTemplate).send(any(), any(TaskStatusChange.class));
    }

    @Test
    void testDeleteTask() {
        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }
}
