package com.test.test.task_details.model;


import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table (name = "task_details")
public class Task {

    @Id
    private Long taskId;
    private String taskTitle;
    private String taskDescription;
    private String taskStatus;
    private Date taskDate;
    private String taskPriority;
    private Date taskCreated;
    private Date updatedAt;
    private Long userId;
}