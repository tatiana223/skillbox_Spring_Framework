package com.example.skillboxsevenapp.repository;

import com.example.skillboxsevenapp.model.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}
