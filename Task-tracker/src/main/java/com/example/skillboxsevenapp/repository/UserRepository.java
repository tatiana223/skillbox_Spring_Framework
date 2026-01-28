package com.example.skillboxsevenapp.repository;

import com.example.skillboxsevenapp.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
