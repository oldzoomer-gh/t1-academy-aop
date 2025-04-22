package ru.gavrilovegor519.t1_academy_aop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import ru.gavrilovegor519.t1_academy_aop.dto.TaskDto;
import ru.gavrilovegor519.t1_academy_aop.enums.TaskStatus;

import java.time.Duration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("testdb")
            .withExposedPorts(5432)
            .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(20)));
    @Container
    public static GenericContainer<?> kafkaContainer = new KafkaContainer(DockerImageName.parse("apache/kafka"))
            .withExposedPorts(9092)
            .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(20)));
    @Container
    public static GenericContainer<?> smtpContainer = new GenericContainer<>(DockerImageName.parse("mailhog/mailhog"))
            .withExposedPorts(1025, 8025)
            .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(20)));

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static TaskDto taskDto;
    private static TaskDto updatedTaskDto;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.kafka.bootstrap-servers", () -> kafkaContainer.getHost() + ":" + kafkaContainer.getMappedPort(9092));
        registry.add("spring.mail.host", smtpContainer::getHost);
        registry.add("spring.mail.port", () -> smtpContainer.getMappedPort(1025));
        registry.add("spring.mail.properties.mail.smtp.auth", () -> "false");
        registry.add("spring.mail.properties.mail.smtp.starttls.enable", () -> "false");
        registry.add("spring.mail.from", () -> "test@1.ru");
    }

    @BeforeAll
    static void setUp() {
        taskDto = new TaskDto();
        taskDto.setAuthorEmail("author@mail.ru");
        taskDto.setExecutorEmail("executor@mail.ru");
        taskDto.setName("Task name");
        taskDto.setDescription("Task description");
        taskDto.setTaskStatus(TaskStatus.NEW);

        updatedTaskDto = new TaskDto();
        updatedTaskDto.setAuthorEmail("author@mail.ru");
        updatedTaskDto.setExecutorEmail("executor@mail.ru");
        updatedTaskDto.setName("Updated task name");
        updatedTaskDto.setDescription("Updated task description");
        updatedTaskDto.setTaskStatus(TaskStatus.IN_PROGRESS);

        postgreSQLContainer.start();
        kafkaContainer.start();
        smtpContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
        kafkaContainer.stop();
        smtpContainer.stop();
    }

    @BeforeEach
    void cleanUp() throws Exception {
        mockMvc.perform(delete("/tasks/all"));
    }

    @Test
    void testCreateTask() throws Exception {
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
                .andExpect(jsonPath("$.name").value("Updated Task name"))
                .andExpect(jsonPath("$.description").value("Updated Task description"))
                .andExpect(jsonPath("$.taskStatus").value("IN_PROGRESS"));
    }

    @Test
    void testDeleteTask() throws Exception {
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
}
