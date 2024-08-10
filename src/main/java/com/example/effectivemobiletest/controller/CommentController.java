package com.example.effectivemobiletest.controller;

import com.example.effectivemobiletest.dto.CommentDto;
import com.example.effectivemobiletest.model.Comment;
import com.example.effectivemobiletest.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    @Operation(
            description = "Этот метод позволяет получить список комментариев, связанных с указанной задачей. " +
                    "Можно использовать параметры для пагинации и фильтрации.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список комментариев успешно возвращен"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача с указанным ID не найдена"
                    )
            },
            security = @SecurityRequirement(name = "Bearer JWT")
    )
    public ResponseEntity<?> getCommentsForTask(@RequestParam Long taskId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);


        Page<Comment> comments = commentService.getCommentsForTask(taskId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);

    }

    @PostMapping("/{taskId}")
    @Operation(
            description = "Этот метод позволяет создать новый комментарий для зависимости задачи. " +
                    "Необходимо указать ID задачи и данные комментария.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Комментарий успешно создан"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные комментария"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача с указанным ID не найдена"
                    )
            },
            security = @SecurityRequirement(name = "Bearer JWT")
    )
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto,
                                           @PathVariable Long taskId) {
        commentService.createComment(taskId, commentDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
