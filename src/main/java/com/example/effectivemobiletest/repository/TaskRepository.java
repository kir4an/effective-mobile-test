package com.example.effectivemobiletest.repository;

import com.example.effectivemobiletest.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Modifying
    @Query("DELETE FROM Task t WHERE t.id = :id")
    void deleteTaskById(Long id);
}
