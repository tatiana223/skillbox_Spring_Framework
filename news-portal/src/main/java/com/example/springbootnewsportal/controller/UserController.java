package com.example.springbootnewsportal.controller;

import com.example.springbootnewsportal.dto.UserDTO;
import com.example.springbootnewsportal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "API для управления пользователями")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Создать пользователя", description = "Создает нового пользователя в системе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
    @PostMapping
    public ResponseEntity<UserDTO.Response> create(@Valid @RequestBody UserDTO.Create userDto) {
        UserDTO.Response response = userService.create(userDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получить всех пользователей", description = "Возвращает список пользователей с пагинацией")
    @GetMapping
    public ResponseEntity<Page<UserDTO.Response>> findAll(Pageable pageable) {
        Page<UserDTO.Response> response = userService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO.Response> findById(@PathVariable Long id) {
        UserDTO.Response user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Обновить пользователя")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO.Update userDto) {
        UserDTO.Response response = userService.updateById(id, userDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
