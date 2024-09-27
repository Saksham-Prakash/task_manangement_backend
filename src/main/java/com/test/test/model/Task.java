package com.test.test.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;  // Unique identifier for the task

    @Column(nullable = false)
    private String title;  // Short description of the task

    @Column(columnDefinition = "TEXT")
    private String description;  // Detailed information about the task

    @Column(nullable = false)
    private String status;  // Task status (e.g., Pending, In Progress, Completed)

    @Column(name = "due_date")
    private LocalDateTime dueDate;  // Task deadline

    @Column(nullable = false)
    private String priority;  // Priority level of the task (e.g., Low, Medium, High)

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;  // Date and time when the task was created

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Date and time when the task was last updated

    @Column(name = "user_id", nullable = false)
    private Long userId;  // ID of the user who created the task (tasks should be user-specific)



}