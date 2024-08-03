package com.example.effectivemobiletest.controller;

import com.example.effectivemobiletest.dto.TaskDto;
import com.example.effectivemobiletest.dto.TaskStatusDto;
import com.example.effectivemobiletest.exception.TaskNotFoundException;
import com.example.effectivemobiletest.model.SecurityUser;
import com.example.effectivemobiletest.model.Task;
import com.example.effectivemobiletest.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

//Система должна обеспечивать создание, редактирование, удаление и просмотр задач
@RestController
@RequestMapping("api/v1/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskDto taskDto){
        taskService.createTask(taskDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @GetMapping
    public ResponseEntity<?> getAllTasks(){
        List<Task> taskList = taskService.getAllTasks();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskList);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestParam int taskId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable Long taskId) {
        Task task = taskService.findTaskById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(task);
    }
    @PostMapping("/change")
    public ResponseEntity<?> changeTaskStatus(@RequestBody @Valid TaskStatusDto taskStatusDto,
                                              @AuthenticationPrincipal SecurityUser securityUser){
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
    @PostMapping("/change/executor/{executorId}")
    public ResponseEntity<?> changeExecutorTask(@PathVariable int executorId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
