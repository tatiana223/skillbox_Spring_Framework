package com.example.skillboxsevenapp.service;

import com.example.skillboxsevenapp.model.Task;
import com.example.skillboxsevenapp.model.User;
import com.example.skillboxsevenapp.repository.TaskRepository;
import com.example.skillboxsevenapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    public final TaskRepository taskRepository;
    public final UserRepository userRepository;

    public Flux<Task> findAll() {
        return taskRepository.findAll()
                .flatMap(this::populateTaskUser);
    }

    public Mono<Task> findById(String id) {
        return taskRepository.findById(id)
                .flatMap(this::populateTaskUser);
    }

    public Mono<Task> create(Task task) {
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        return taskRepository.save(task);
    }

    public Mono<Task> update(String id, Task updateTask) {
        return taskRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(updateTask.getName());
                    existing.setDescription(updateTask.getDescription());
                    existing.setStatus(updateTask.getStatus());
                    existing.setAssigneeId(updateTask.getAssigneeId());
                    existing.setObserverIds(updateTask.getObserverIds());
                    existing.setUpdatedAt(Instant.now());
                    return taskRepository.save(existing);
                });

    }

    public Mono<Task> addObserver(String taskId, String userId) {
        return taskRepository.findById(taskId)
                .flatMap(task -> {
                    task.getObserverIds().add(userId);
                    task.setUpdatedAt(Instant.now());
                    return taskRepository.save(task);
                })
                .flatMap(this::populateTaskUser);
    }

    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

    public Mono<Task> populateTaskUser(Task task) {
        Mono<User> authorMono = task.getAuthorId() != null ?
                userRepository.findById(task.getAuthorId()) :
                Mono.justOrEmpty(null);

        Mono<User> assigneeMono = task.getAssigneeId() != null ?
                userRepository.findById(task.getAssigneeId()) :
                Mono.justOrEmpty(null);

        Mono<Set<User>> observersMono = Flux.fromIterable(task.getObserverIds())
                .flatMap(userRepository::findById)
                .collect(Collectors.toSet());

        return Mono.zip(authorMono, assigneeMono, observersMono)
                .map(tuple -> {
                    task.setAuthor(tuple.getT1());
                    task.setAssignee(tuple.getT2());
                    task.setObservers(tuple.getT3());
                    return task;
                });
    }


}
