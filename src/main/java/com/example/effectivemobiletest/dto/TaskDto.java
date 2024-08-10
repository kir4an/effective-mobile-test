package com.example.effectivemobiletest.dto;

import com.example.effectivemobiletest.model.TaskPriority;
import com.example.effectivemobiletest.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
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
}
