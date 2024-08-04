package com.example.effectivemobiletest.dto;

import com.example.effectivemobiletest.model.TaskStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TaskStatusDto {
    private Long taskId;
    private TaskStatus taskStatus;
}
