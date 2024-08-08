package com.example.effectivemobiletest.exception;

public class TaskStatusNotExistException extends RuntimeException {
    public TaskStatusNotExistException(String message) {
        super(message);
    }
}
