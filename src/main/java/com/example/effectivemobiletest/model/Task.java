package com.example.effectivemobiletest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column
    private TaskPriority taskPriority;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private User author;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    @JsonBackReference
    private User executor;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;
}