package ru.gavrilovegor519.t1_academy_aop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilovegor519.t1_academy_aop.config.TestContainersConfig;
import ru.gavrilovegor519.t1_academy_aop.dto.TaskDto;
import ru.gavrilovegor519.t1_academy_aop.enums.TaskStatus;
import ru.gavrilovegor519.t1_academy_aop.repository.TaskRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerIntegrationTest extends TestContainersConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void cleanUp() {
        taskRepository.deleteAll();
    }

    @Test
    void testCreateTask() throws Exception {
        TaskDto taskDto = createTaskDto("author@mail.ru", "executor@mail.ru", "Task name", "Task description", TaskStatus.NEW);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.authorEmail").value("author@mail.ru"))
                .andExpect(jsonPath("$.executorEmail").value("executor@mail.ru"))
                .andExpect(jsonPath("$.name").value("Task name"))
                .andExpect(jsonPath("$.description").value("Task description"))
                .andExpect(jsonPath("$.taskStatus").value("NEW"));
    }

    @Test
    void testGetAllTasks() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetTaskById() throws Exception {
        TaskDto taskDto = createTaskDto("author@mail.ru", "executor@mail.ru", "Task name", "Task description", TaskStatus.NEW);

        String response = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Long taskId = Long.parseLong(response.split("\"id\":")[1].split(",")[0]);

        mockMvc.perform(get("/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.authorEmail").value("author@mail.ru"))
                .andExpect(jsonPath("$.executorEmail").value("executor@mail.ru"))
                .andExpect(jsonPath("$.name").value("Task name"))
                .andExpect(jsonPath("$.description").value("Task description"))
                .andExpect(jsonPath("$.taskStatus").value("NEW"));
    }

    @Test
    void testUpdateTask() throws Exception {
        TaskDto taskDto = createTaskDto("author@mail.ru", "executor@mail.ru", "Task name", "Task description", TaskStatus.NEW);
        TaskDto updatedTaskDto = createTaskDto("author@mail.ru", "executor@mail.ru", "Updated task name", "Updated task description", TaskStatus.IN_PROGRESS);

        String response = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Long taskId = Long.parseLong(response.split("\"id\":")[1].split(",")[0]);

        mockMvc.perform(put("/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTaskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.authorEmail").value("author@mail.ru"))
                .andExpect(jsonPath("$.executorEmail").value("executor@mail.ru"))
                .andExpect(jsonPath("$.name").value("Updated task name"))
                .andExpect(jsonPath("$.description").value("Updated task description"))
                .andExpect(jsonPath("$.taskStatus").value("IN_PROGRESS"));
    }

    @Test
    void testDeleteTask() throws Exception {
        TaskDto taskDto = createTaskDto("author@mail.ru", "executor@mail.ru", "Task name", "Task description", TaskStatus.NEW);

        String response = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        long taskId = Long.parseLong(response.split("\"id\":")[1].split(",")[0]);

        mockMvc.perform(delete("/tasks/" + taskId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tasks/" + taskId))
                .andExpect(status().isNotFound());
    }

    private TaskDto createTaskDto(String authorEmail, String executorEmail, String name, String description, TaskStatus taskStatus) {
        TaskDto taskDto = new TaskDto();
        taskDto.setAuthorEmail(authorEmail);
        taskDto.setExecutorEmail(executorEmail);
        taskDto.setName(name);
        taskDto.setDescription(description);
        taskDto.setTaskStatus(taskStatus);
        return taskDto;
    }
}
