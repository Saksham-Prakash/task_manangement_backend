package com.test.test.controller;


import com.test.test.model.Task;
import com.test.test.model.User;
import com.test.test.repository.UserRepo;
import com.test.test.service.TaskService;
import com.test.test.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo userRepo;  // Inject UserRepo to retrieve user details

    // Endpoint to create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, HttpServletRequest request) {
        try {
            // Extract the JWT token from the Authorization header
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            String token = authorizationHeader.substring(7);  // Remove "Bearer " from the token
            String username = jwtUtil.extractUsername(token);  // Extract username from JWT

            // Log the username to ensure it's being extracted properly
            System.out.println("Extracted username from JWT: " + username);

            // Find the user by username
            User user = userRepo.findByUserName(username);
            if (user == null) {
                // Log error and return a 404 if user is not found
                System.out.println("User not found with username: " + username);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // 404 Not Found
            }

            // Set the userId for the task
            task.setUserId(user.getId());

            // Create the task
            Task createdTask = taskService.createTask(task);

            // Log the created task ID
            System.out.println("Created Task ID: " + createdTask.getTaskId());

            // Return the created task with status 201
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);

        } catch (Exception e) {
            // Log the exception details
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 Internal Server Error in case of failure
        }
    }

    // New endpoint to get all tasks for the authenticated user
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(HttpServletRequest request) {
        try {
            // Extract the JWT token from the Authorization header
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            String token = authorizationHeader.substring(7);  // Remove "Bearer " from the token
            String username = jwtUtil.extractUsername(token);  // Extract username from JWT

            // Find the user by username
            User user = userRepo.findByUserName(username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // User not found
            }

            // Fetch tasks for the user
            List<Task> tasks = taskService.getTasksByUserId(user.getId());
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        try {
            // Fetch the task by ID
            Optional<Task> task = taskService.getTaskById(taskId);
            if (task.isPresent()) {
                return new ResponseEntity<>(task.get(), HttpStatus.OK);  // Task found
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Task not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        try {
            Task updated = taskService.updateTask(taskId, updatedTask);
            if (updated != null) {
                return new ResponseEntity<>(updated, HttpStatus.OK);  // Task updated successfully
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Task not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        try {
            boolean isDeleted = taskService.deleteTask(taskId);
            if (isDeleted) {
                return new ResponseEntity<>("Task deleted", HttpStatus.NO_CONTENT); // Task deleted successfully
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Task not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
