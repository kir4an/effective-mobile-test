package com.example.effectivemobiletest;

import com.example.effectivemobiletest.dto.LoginRequestDto;
import com.example.effectivemobiletest.dto.TaskDto;
import com.example.effectivemobiletest.dto.TaskStatusDto;
import com.example.effectivemobiletest.dto.TokenResponseDto;
import com.example.effectivemobiletest.model.Task;
import com.example.effectivemobiletest.model.TaskPriority;
import com.example.effectivemobiletest.model.TaskStatus;
import com.example.effectivemobiletest.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(value = "/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/init_for_task_controller.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTestIT extends AbstractTestContainers {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository taskRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void deleteTask_WithValidToken_ShouldReturnOk() throws Exception {
        Long taskIdToDelete = 1L;
        var result = delete("/api/v1/task")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("user1@example.com", "string"))
                .param("taskId", taskIdToDelete.toString());

        this.mockMvc.perform(result).andExpect(status().isOk());

        List<Task> currentTasks = taskRepository.findAll();
        assertThat(currentTasks).hasSize(4);
    }

    @Test
    void createTask_WithValidToken_ReturnsCreated() throws Exception {
        TaskDto createTaskDto = TaskDto.builder()
                .description("Random_description")
                .title("Random_title")
                .status(TaskStatus.WAITING)
                .taskPriority(TaskPriority.LOW)
                .build();
        var result = post("/api/v1/task")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "
                        + obtainAccessToken("user1@example.com", "string"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTaskDto));

        this.mockMvc.perform(result).andExpect
                (status().isCreated());

    }

    @Test
    void getAllTasks_WithValidToken_ReturnsAllTasks() throws Exception {
        var result = get("/api/v1/task")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "
                        + obtainAccessToken("user1@example.com", "string"))
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(result).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    void deleteTask_WithInvalidAuthor_ShouldReturnForbidden() throws Exception {
        Long taskIdToDelete = 3L;

        var result = delete("/api/v1/task")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("user3@example.com", "string"))
                .param("taskId", String.valueOf(taskIdToDelete));

        this.mockMvc.perform(result).andExpect(status().isForbidden());


    }

    @Test
    void getTask_WithValidId_ReturnsOkAndTask() throws Exception {
        Long validTaskId = 1L;

        var result = get("/api/v1/task/" + validTaskId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("user1@example.com", "string"))
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(validTaskId));
    }

    @Test
    void getTask_WithInvalidId_ReturnsNotFound() throws Exception {
        Long invalidTaskId = 999L;

        var result = get("/api/v1/task/" + invalidTaskId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("user1@example.com", "string"))
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(result)
                .andExpect(status().isNotFound());
    }

    @Test
    void changeTaskStatus_WithValidData_ReturnsOk() throws Exception {
        TaskStatusDto taskStatusDto = new TaskStatusDto();
        taskStatusDto.setTaskId(1L);
        taskStatusDto.setTaskStatus(TaskStatus.IN_PROGRESS);

        var result = post("/api/v1/task/change")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("user1@example.com", "string"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskStatusDto));

        this.mockMvc.perform(result)
                .andExpect(status().isOk());
    }

    @Test
    void changeTaskStatus_UserIsNotAuthorOrExecutor_ReturnsForbidden() throws Exception {
        TaskStatusDto taskStatusDto = new TaskStatusDto();
        taskStatusDto.setTaskId(3L);
        taskStatusDto.setTaskStatus(TaskStatus.COMPLETED);

        var result = post("/api/v1/task/change")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("user1@example.com", "string"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskStatusDto));

        this.mockMvc.perform(result)
                .andExpect(status().isForbidden());
    }

    @Test
    void assignExecutorTask_WithValidData_ReturnsOk() throws Exception {
        Long taskId = 1L;
        Long executorId = 2L;

        var result = post("/api/v1/task/assign/executor/" + taskId + "/" + executorId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("user1@example.com", "string")) // Убедитесь, что user1 является автором задачи с ID 1
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(result).andExpect(
                status().isOk()
        );
        Task task = taskRepository.findById(taskId).orElse(null);
        Assertions.assertEquals(executorId, task.getExecutor().getId());
    }

    private String obtainAccessToken(String email, String password) throws Exception {
        LoginRequestDto preparedRequest = new LoginRequestDto(email, password);

        var requestBuilder = post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(preparedRequest));

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        TokenResponseDto token = objectMapper.readValue(result.getResponse().getContentAsString(), TokenResponseDto.class);
        return token.getAccessToken();
    }
}