package com.example.skillboxsevenapp.dto;

import com.example.skillboxsevenapp.model.TaskStatus;
import lombok.Data;

import java.util.Set;

@Data
public class TaskRequestDto {
    private String name;
    private String description;
    private TaskStatus status;

    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;
}
