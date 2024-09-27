package com.test.test.service;

import com.test.test.model.Task;
import com.test.test.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        // Set the created_at and updated_at timestamps (optional if using @PrePersist/@PreUpdate)
        task.setCreatedAt(java.time.LocalDateTime.now());
        task.setUpdatedAt(java.time.LocalDateTime.now());

        // Save the task to the database
        return taskRepository.save(task);
    }

    // Method to get tasks by user ID
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId); // Implement this method in your Task repository
    }

    // New method to get a specific task by its ID
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId); // Optional to handle the case where task doesn't exist
    }

    // New method to update a task by its ID
    public Task updateTask(Long taskId, Task updatedTask) {
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setPriority(updatedTask.getPriority());
            return taskRepository.save(existingTask); // Save the updated task
        } else {
            return null; // Task not found
        }
    }

    // New method to delete a task by its ID
    public boolean deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId); // Delete the task
            return true; // Deletion successful
        }
        return false; // Task not found
    }
}
