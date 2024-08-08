package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.CommentDto;
import com.example.effectivemobiletest.exception.TaskNotFoundException;
import com.example.effectivemobiletest.exception.UserNotFoundException;
import com.example.effectivemobiletest.model.Comment;
import com.example.effectivemobiletest.model.Task;
import com.example.effectivemobiletest.model.User;
import com.example.effectivemobiletest.repository.CommentRepository;
import com.example.effectivemobiletest.repository.TaskRepository;
import com.example.effectivemobiletest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Comment> getCommentsForTask(Long taskId, Pageable pageable) {
        return commentRepository.findByTaskId(taskId, pageable);
    }

    @Override
    public void createComment(Long taskId, CommentDto commentDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()->new TaskNotFoundException("Task with id " + taskId + " not found"));

        String currentUserName = getCurrentUser();
        User user = userRepository.findUserByUsername(currentUserName)
                .orElseThrow(()->new UserNotFoundException("User " + currentUserName + " not found"));

        Comment comment = Comment.builder()
                .task(task)
                .author(user)
                .content(commentDto.getComment())
                .build();
        task.getComments().add(comment);
        commentRepository.save(comment);
    }
    private static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
