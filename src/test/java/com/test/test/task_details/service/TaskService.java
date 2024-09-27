package com.test.test.task_details.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.test.task_details.repository.TaskRepo;


@Service
public class TaskService {
    
    @Autowired
    private TaskRepo taskRepo;
}
