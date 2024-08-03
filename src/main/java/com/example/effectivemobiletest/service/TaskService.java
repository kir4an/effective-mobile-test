package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.TaskDto;
import com.example.effectivemobiletest.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    void createTask(TaskDto taskDto);

    List<Task> getAllTasks();

    Optional<Task> findTaskById(Long id);

    void deleteTaskById(Long id);
}
