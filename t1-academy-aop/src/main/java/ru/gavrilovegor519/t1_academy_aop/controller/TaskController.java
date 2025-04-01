package ru.gavrilovegor519.t1_academy_aop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gavrilovegor519.t1_academy_aop.dto.TaskDto;
import ru.gavrilovegor519.t1_academy_aop.mapping.TaskMapper;
import ru.gavrilovegor519.t1_academy_aop.service.TaskService;

import java.util.List;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskMapper.toDto(taskService.getAllTasks());
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto task) {
        return taskMapper.toDto(taskService.createTask(taskMapper.toEntity(task)));
    }
    
    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskMapper.toDto(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskDto task) {
        return taskMapper.toDto(taskService.updateTask(id, taskMapper.toEntity(task)));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
