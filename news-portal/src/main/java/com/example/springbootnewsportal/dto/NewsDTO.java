package com.example.springbootnewsportal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

public class NewsDTO {

    @Data
    @Schema(name = "NewsCreate")
    public static class Create {
        @Schema(description = "Заголовок новости", example = "Новые технологии в Java", required = true)
        @NotBlank(message = "Title is required")
        private String title;

        @Schema(description = "Содержание новости", example = "Spring Boot 3 представляет новые возможности...", required = true)
        @NotBlank(message = "Content is required")
        private String content;

        @Schema(description = "ID категории", example = "1", required = true)
        @NotNull(message = "Category ID is required")
        private Long categoryId;
    }

    @Data
    @Schema(name = "NewsResponse")
    public static class Response {
        @Schema(description = "ID новости", example = "1")
        private Long id;

        @Schema(description = "Заголовок новости", example = "Новые технологии в Java")
        private String title;

        @Schema(description = "Содержание новости", example = "Spring Boot 3 представляет новые возможности...")
        private String content;

        @Schema(description = "Автор новости")
        private UserDTO.Response author;

        @Schema(description = "Категория новости")
        private CategoryDTO.Response category;

        @Schema(description = "Дата создания", example = "2024-01-15T10:30:00")
        private String createdAt;

        @Schema(description = "Дата обновления", example = "2024-01-15T11:30:00")
        private String updatedAt;

        @Schema(description = "Количество комментариев", example = "5")
        private Long commentCount;
    }

    @Data
    @Schema(name = "NewsDetailedResponse")
    public static class DetailedResponse {
        @Schema(description = "ID новости", example = "1")
        private Long id;

        @Schema(description = "Заголовок новости", example = "Новые технологии в Java")
        private String title;

        @Schema(description = "Содержание новости", example = "Spring Boot 3 представляет новые возможности...")
        private String content;

        @Schema(description = "Автор новости")
        private UserDTO.Response author;

        @Schema(description = "Категория новости")
        private CategoryDTO.Response category;

        @Schema(description = "Дата создания", example = "2024-01-15T10:30:00")
        private String createdAt;

        @Schema(description = "Дата обновления", example = "2024-01-15T11:30:00")
        private String updatedAt;

        @Schema(description = "Список комментариев")
        private List<CommentDTO.Response> comments;
    }

    @Data
    @Schema(name = "NewsUpdate")
    public static class Update {
        @Schema(description = "Заголовок новости", example = "Обновленный заголовок")
        private String title;

        @Schema(description = "Содержание новости", example = "Обновленное содержание новости...")
        private String content;

        @Schema(description = "ID категории", example = "2")
        private Long categoryId;
    }
}
