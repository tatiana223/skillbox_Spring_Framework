package com.example.springbootnewsportal.controller;

import com.example.springbootnewsportal.aop.CheckOwnership;
import com.example.springbootnewsportal.dto.NewsDTO;
import com.example.springbootnewsportal.service.NewsService;
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
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Tag(name = "News", description = "API для управления новостями")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "Создать новость", description = "Создает новую новость. Автор определяется автоматически из текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новость успешно создана"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные или категория не найдена")
    })
    @PostMapping
    public ResponseEntity<NewsDTO.Response> create(@Valid @RequestBody NewsDTO.Create newsDto) {
        // Временная заглушка - в реальном приложении берется из SecurityContext
        String currentUsername = "currentUser";
        NewsDTO.Response response = newsService.create(newsDto, currentUsername);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получить все новости", description = "Возвращает список новостей с пагинацией и фильтрацией по категории и автору")
    @ApiResponse(responseCode = "200", description = "Список новостей успешно получен")
    @GetMapping
    public ResponseEntity<Page<NewsDTO.Response>> findAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long authorId,
            Pageable pageable) {
        Page<NewsDTO.Response> news = newsService.findAll(categoryId, authorId, pageable);
        return ResponseEntity.ok(news);
    }

    @Operation(summary = "Получить новость по ID", description = "Возвращает новость по её идентификатору со всеми комментариями")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новость найдена"),
            @ApiResponse(responseCode = "404", description = "Новость не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO.DetailedResponse> findById(@PathVariable Long id) {
        NewsDTO.DetailedResponse news = newsService.findById(id);
        return ResponseEntity.ok(news);
    }

    @Operation(summary = "Обновить новость", description = "Обновляет данные новости. Только владелец может обновить")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новость успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Новость не найдена"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для обновления")
    })
    @PutMapping("/{id}")
    @CheckOwnership(entityType = "news", idParam = "id")
    public ResponseEntity<NewsDTO.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody NewsDTO.Update newsDto) {
        NewsDTO.Response response = newsService.update(id, newsDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Удалить новость", description = "Удаляет новость по ID. Только владелец может удалить")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Новость успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Новость не найдена"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для удаления")
    })
    @DeleteMapping("/{id}")
    @CheckOwnership(entityType = "news", idParam = "id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
