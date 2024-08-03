package com.example.effectivemobiletest.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Task> createdTasks = new ArrayList<>();
    @OneToMany(mappedBy = "executor",cascade = CascadeType.ALL)
    private List<Task> assignedTasks = new ArrayList<>();
}
