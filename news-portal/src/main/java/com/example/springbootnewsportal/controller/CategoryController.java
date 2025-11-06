package com.example.springbootnewsportal.controller;

import com.example.springbootnewsportal.dto.CategoryDTO;
import com.example.springbootnewsportal.service.CategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "API для управления категориями новостей")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Создать категорию", description = "Создает новую категорию новостей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория успешно создана"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные или категория уже существует")
    })
    @PostMapping
    public ResponseEntity<CategoryDTO.Response> create(@Valid @RequestBody CategoryDTO.Create categoryDto) {
        CategoryDTO.Response response = categoryService.create(categoryDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получить все категории", description = "Возвращает список категорий с пагинацией")
    @ApiResponse(responseCode = "200", description = "Список категорий успешно получен")
    @GetMapping
    public ResponseEntity<Page<CategoryDTO.Response>> findAll(Pageable pageable) {
        Page<CategoryDTO.Response> categories = categoryService.findAll(pageable);
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Получить категорию по ID", description = "Возвращает категорию по её идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория найдена"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO.Response> findById(@PathVariable Long id) {
        CategoryDTO.Response category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Обновить категорию", description = "Обновляет данные категории")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO.Update categoryDto) {
        CategoryDTO.Response response = categoryService.update(id, categoryDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Удалить категорию", description = "Удаляет категорию по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Категория успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена"),
            @ApiResponse(responseCode = "400", description = "Невозможно удалить категорию с привязанными новостями")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
