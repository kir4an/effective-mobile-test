package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.TaskDto;
import com.example.effectivemobiletest.dto.TaskStatusDto;
import com.example.effectivemobiletest.exception.TaskNotFoundException;
import com.example.effectivemobiletest.exception.TaskStatusNotExistException;
import com.example.effectivemobiletest.exception.UserNotFoundException;
import com.example.effectivemobiletest.model.Task;
import com.example.effectivemobiletest.model.TaskStatus;
import com.example.effectivemobiletest.model.User;
import com.example.effectivemobiletest.repository.TaskRepository;
import com.example.effectivemobiletest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createTask(TaskDto taskDto) {
        User user = userRepository.findUserByUsername(getCurrentUser())
                .orElseThrow();
        Task task = Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .taskPriority(taskDto.getTaskPriority())
                .status(taskDto.getStatus())
                .author(user)
                .build();
        taskRepository.save(task);
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

    @Override
    public void assignTaskToUser(Long taskId, Long userId) {
        Task task = getTask(taskId);
        User user = getUser(userId);
        task.setExecutor(user);
        taskRepository.save(task);
    }


    @Override
    public void changeTaskStatus(TaskStatusDto taskStatusDto,User currentUser) {
        Task task = getTask(taskStatusDto.getTaskId());
        validTaskStatus(taskStatusDto);
        if (!task.getAuthor().equals(currentUser) && !task.getExecutor().equals(currentUser)) {
            throw new AccessDeniedException("You are not allowed to change this task");
        }
        task.setStatus(taskStatusDto.getTaskStatus());
        taskRepository.save(task);

    }

    public void isUserAuthor(Long taskId) {
        String currentUserName = getCurrentUser();
        User currentUser = userRepository.findUserByUsername(currentUserName)
                .orElseThrow(() -> new UserNotFoundException("User " + currentUserName + " not found"));

        Task task = getTask(taskId);

        if (!task.getAuthor().equals(currentUser)) {
            throw new AccessDeniedException("User is not the author of the task");
        }
    }

    private Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " not found"));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
    }

    private static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    private static void validTaskStatus(TaskStatusDto taskStatusDto) {
        for(TaskStatus taskStatus:TaskStatus.values()){
            if(taskStatusDto.getTaskStatus().equals(taskStatus)){
                break;
            }else{
                throw new TaskStatusNotExistException("TaskStatus " + taskStatusDto.getTaskStatus() + "not exist");
            }
        }
    }
}
