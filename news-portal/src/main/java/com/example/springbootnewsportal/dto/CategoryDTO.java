package com.example.springbootnewsportal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class CategoryDTO {

    @Data
    @Schema(name = "CategoryCreate")
    public static class Create {

        @Schema(description = "Название категории", example = "Технологии", required = true)
        @NotBlank(message = "Name is required")
        private String name;

        @Schema(description = "Описание категории", example = "Новости о технологиях и IT")
        private String description;

    }

    @Data
    @Schema(name = "CategoryResponse")
    public static class Response {

        @Schema(description = "ID категории", example = "1")
        private Long id;

        @Schema(description = "Название категории", example = "Технологии")
        private String name;

        @Schema(description = "Описание категории", example = "Новости о технологиях и IT")
        private String description;
    }

    @Data
    @Schema(name = "CategoryUpdate")
    public static class Update {
        @Schema(description = "Название категории", example = "IT Технологии")
        private String name;

        @Schema(description = "Описание категории", example = "Обновленное описание технологий")
        private String description;
    }
}
