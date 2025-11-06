package com.example.springbootnewsportal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class CommentDTO {

    @Data
    @Schema(name = "CommentCreate")
    public static class Create {
        @Schema(description = "Текст комментария", example = "Отличная статья!", required = true)
        @NotBlank(message = "Content is required")
        private String content;

        @Schema(description = "ID новости", example = "1", required = true)
        @NotNull(message = "News ID is required")
        private Long newsId;
    }

    @Data
    @Schema(name = "CommentResponse")
    public static class Response {
        @Schema(description = "ID комментария", example = "1")
        private Long id;

        @Schema(description = "Текст комментария", example = "Отличная статья!")
        private String content;

        @Schema(description = "Автор комментария")
        private UserDTO.Response author;

        @Schema(description = "Дата создания", example = "2024-01-15T10:30:00")
        private String createdAt;

        @Schema(description = "Дата обновления", example = "2024-01-15T11:30:00")
        private String updatedAt;
    }

    @Data
    @Schema(name = "CommentUpdate")
    public static class Update {
        @Schema(description = "Текст комментария", example = "Обновленный комментарий", required = true)
        @NotBlank(message = "Content is required")
        private String content;
    }
}
