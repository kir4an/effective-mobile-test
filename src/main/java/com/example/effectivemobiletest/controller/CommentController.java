package com.example.effectivemobiletest.controller;

import com.example.effectivemobiletest.dto.CommentDto;
import com.example.effectivemobiletest.model.Comment;
import com.example.effectivemobiletest.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getCommentsForTask(@RequestParam Long taskId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);


        Page<Comment> comments = commentService.getCommentsForTask(taskId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);

    }

    @PostMapping("/{taskId}")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto,
                                           @PathVariable Long taskId) {
        commentService.createComment(taskId,commentDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
