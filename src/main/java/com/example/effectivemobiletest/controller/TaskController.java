package com.example.effectivemobiletest.controller;

import com.example.effectivemobiletest.dto.TaskDto;
import com.example.effectivemobiletest.dto.TaskStatusDto;
import com.example.effectivemobiletest.exception.TaskNotFoundException;
import com.example.effectivemobiletest.model.SecurityUser;
import com.example.effectivemobiletest.model.Task;
import com.example.effectivemobiletest.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(
            description = "Создание новой задачи. " +
                    "Этот метод позволяет создавать задачу с заданными атрибутами. " +
                    "Необходимо указать заголовок, описание, приоритет и статус задачи.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Задача успешно создана"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные задачи"
                    )
            },
            security = @SecurityRequirement(name = "Bearer JWT")
    )
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskDto taskDto) {
        taskService.createTask(taskDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    @Operation(
            description = "Получение всех задач. Этот метод возвращает список всех задач, " +
                    "которые находятся в системе. Результат может быть пустым, если " +
                    "задачи не найдены.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ. Возвращает список задач.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Task.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задачи не найдены."
                    )
            }
    )
    public ResponseEntity<?> getAllTasks() {
        List<Task> taskList = taskService.getAllTasks();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskList);
    }

    @DeleteMapping
    @PreAuthorize("@taskService.isUserAuthor(#taskId)")
    @Operation(
            description = "Удаление задачи по ID. Этот метод позволяет удалить задачу, " +
                    "если текущий пользователь является её автором. Необходимо передать ID " +
                    "задачи, которую нужно удалить.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача успешно удалена."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача с указанным ID не найдена."
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Доступ запрещен. Пользователь не является автором задачи."
                    )
            }
    )
    public ResponseEntity<?> deleteTask(@RequestParam Long taskId) {
        taskService.deleteTaskById(taskId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/{taskId}")
    @Operation(
            description = "Получение задачи по ID. Этот метод возвращает задачу с указанным ID. " +
                    "Если задача не найдена, будет возвращено исключение.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ. Возвращает задачу."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача с указанным ID не найдена."
                    )
            }
    )
    public ResponseEntity<Task> getTask(@PathVariable Long taskId) {
        Task task = taskService.findTaskById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(task);
    }

    @PostMapping("/change")
    @Operation(
            description = "Изменение статуса задачи. Этот метод позволяет изменить статус задачи, " +
                    "если текущий пользователь является автором или исполнителем задачи.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Статус задачи успешно изменен."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача с указанным ID не найдена."
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Доступ запрещен. Пользователь не является автором или исполнителем задачи."
                    )
            }
    )
    public ResponseEntity<?> changeTaskStatus(@RequestBody @Valid TaskStatusDto taskStatusDto,
                                              @AuthenticationPrincipal SecurityUser securityUser) {
        taskService.changeTaskStatus(taskStatusDto, securityUser.getUser());

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("assign/executor/{taskId}/{executorId}")
    @PreAuthorize("@taskService.isUserAuthor(#taskId)")
    @Operation(
            description = "Назначение исполнителя на задачу. Этот метод позволяет назначить исполнителя " +
                    "для указанной задачи. Доступно только автору задачи.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Исполнитель успешно назначен на задачу."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача или пользователь с указанным ID не найдены."
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Доступ запрещен. Пользователь не является автором задачи."
                    )
            }
    )
    public ResponseEntity<?> assignExecutorTask(@PathVariable Long taskId,
                                                @PathVariable Long executorId) {


        taskService.assignTaskToUser(taskId, executorId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
