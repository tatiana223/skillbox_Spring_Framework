package com.example.skillboxsevenapp.controller;

import com.example.skillboxsevenapp.dto.TaskRequestDto;
import com.example.skillboxsevenapp.dto.TaskResponseDto;
import com.example.skillboxsevenapp.mapper.TaskMapper;
import com.example.skillboxsevenapp.model.Task;
import com.example.skillboxsevenapp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public Flux<TaskResponseDto> findAll() {
        return taskService.findAll()
                .map(taskMapper::toResponseDto);
    }

    @GetMapping("/{id}")
    public Mono<TaskResponseDto> findById(@PathVariable  String id) {
        return taskService.findById(id)
                .map(taskMapper::toResponseDto);
    }

    @PostMapping
    public Mono<TaskResponseDto> create(@RequestBody TaskRequestDto taskDto) {
        return taskService.create(taskMapper.toEntity(taskDto))
                .flatMap(taskService::populateTaskUser)
                .map(taskMapper::toResponseDto);
    }

    @PutMapping("/{id}")
    public Mono<TaskResponseDto> update(@PathVariable String id, @RequestBody TaskRequestDto taskDto) {
        return taskService.update(id, taskMapper.toEntity(taskDto))
                .flatMap(taskService::populateTaskUser)
                .map(taskMapper::toResponseDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return taskService.deleteById(id);
    }
}
