package com.example.effectivemobiletest.repository;

import com.example.effectivemobiletest.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
