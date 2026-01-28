package com.example.skillboxsevenapp.controller;

import com.example.skillboxsevenapp.dto.UserRequestDto;
import com.example.skillboxsevenapp.dto.UserResponseDto;
import com.example.skillboxsevenapp.mapper.UserMapper;
import com.example.skillboxsevenapp.model.User;
import com.example.skillboxsevenapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Flux<UserResponseDto> getAllUsers() {
        return userService.findAll()
                .map(userMapper::toResponseDto);
    }

    @GetMapping("/{id}")
    public Mono<UserResponseDto> getUserBuId(@PathVariable String id) {
        return userService.findById(id)
                .map(userMapper::toResponseDto);
    }

    @PostMapping
    public Mono<UserResponseDto> createUser(@RequestBody UserRequestDto userDto) {
        return userService.create(userMapper.toEntity(userDto))
                .map(userMapper::toResponseDto);
    }

    @PutMapping("/{id}")
    public Mono<UserResponseDto> updateUser(
            @PathVariable String id,
            @RequestBody UserRequestDto userDto
    ) {
        return userService.update(id, userMapper.toEntity(userDto))
                .map(userMapper::toResponseDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userService.deleteById(id);
    }


}
