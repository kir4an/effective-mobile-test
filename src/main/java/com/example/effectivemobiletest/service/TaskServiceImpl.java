package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.TaskDto;
import com.example.effectivemobiletest.model.Task;
import com.example.effectivemobiletest.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
