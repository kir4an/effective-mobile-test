package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.CommentDto;
import com.example.effectivemobiletest.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Page<Comment> getCommentsForTask(Long taskId, Pageable pageable);
    void createComment(Long taskId, CommentDto comment);
}
