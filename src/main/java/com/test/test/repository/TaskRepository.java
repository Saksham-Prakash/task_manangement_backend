package com.test.test.repository;

import com.test.test.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // You can add custom queries if needed
    List<Task> findByUserId(Long userId);

}
