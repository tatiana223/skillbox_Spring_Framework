package com.example.skillboxsevenapp.dto;

import com.example.skillboxsevenapp.model.TaskStatus;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class TaskResponseDto {
    private String id;
    private String name;
    private String description;

    private Instant createdAt;
    private Instant updatedAt;

    private TaskStatus status;

    private UserResponseDto author;
    private UserResponseDto assignee;
    private Set<UserResponseDto> observers;
}
