package com.example.effectivemobiletest.repository;

import com.example.effectivemobiletest.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findByTaskId(Long taskId, Pageable pageable);

    void save(Comment comment);
}
