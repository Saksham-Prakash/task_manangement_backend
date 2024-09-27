package com.test.test.task_details.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.test.task_details.model.Task;


@Repository
public interface TaskRepo extends JpaRepository<Task, Long>{
}