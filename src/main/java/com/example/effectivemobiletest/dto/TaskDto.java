package com.example.effectivemobiletest.dto;

import com.example.effectivemobiletest.model.TaskPriority;
import com.example.effectivemobiletest.model.TaskStatus;
import com.example.effectivemobiletest.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
@Builder
public class TaskDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private TaskStatus status;
    @NotBlank
    private TaskPriority taskPriority;
    private Long executorId;
}
