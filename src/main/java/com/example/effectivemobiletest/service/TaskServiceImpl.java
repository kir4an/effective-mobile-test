package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.TaskDto;
import com.example.effectivemobiletest.model.Task;
import com.example.effectivemobiletest.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;

    @Override
    public void createTask(TaskDto taskDto) {

    }
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @Override
    public Optional<Task> findTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }
    @Override
    public void deleteTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
