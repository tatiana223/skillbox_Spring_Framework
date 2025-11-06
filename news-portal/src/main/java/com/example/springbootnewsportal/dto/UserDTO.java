package com.example.springbootnewsportal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserDTO {

    @Data
    @Schema(name = "UserCreate")
    public static class Create {

        @Schema(description = "Имя пользователя", example = "john_doe", minLength = 3, maxLength = 50, required = true)
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private String username;

        @Schema(description = "Email адрес", example = "john@example.com", required = true)
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;

        @Schema(description = "Пароль", example = "123456", minLength = 6, required = true)
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;
    }

    @Data
    @Schema(name = "UserResponse")
    public static class Response {

        @Schema(description = "ID пользователя", example = "1")
        private Long id;
        @Schema(description = "Имя пользователя", example = "john_doe")
        private String username;
        @Schema(description = "Email адрес", example = "john@example.com")
        private String email;
        @Schema(description = "Дата создания", example = "2024-01-15T10:30:00")
        private String createdAt;
    }

    @Data
    @Schema(name = "UserUpdate")
    public static class Update {

        @Schema(description = "Имя пользователя", example = "john_updated", minLength = 3, maxLength = 50)
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private String username;

        @Schema(description = "Email адрес", example = "john_updated@example.com")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String email;
    }
}
