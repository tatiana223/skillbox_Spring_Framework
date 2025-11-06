package com.example.springbootnewsportal.controller;

import com.example.springbootnewsportal.aop.CheckOwnership;
import com.example.springbootnewsportal.dto.CommentDTO;
import com.example.springbootnewsportal.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "API для управления комментариями")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Создать комментарий", description = "Создает новый комментарий к новости. Автор определяется автоматически из текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные или новость не найдена")
    })
    @PostMapping
    public ResponseEntity<CommentDTO.Response> create(@Valid @RequestBody CommentDTO.Create commentDto) {
        // Временная заглушка - в реальном приложении берется из SecurityContext
        String currentUsername = "currentUser";
        CommentDTO.Response response = commentService.create(commentDto, currentUsername);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получить комментарии к новости", description = "Возвращает список комментариев для конкретной новости (без пагинации)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список комментариев успешно получен"),
            @ApiResponse(responseCode = "404", description = "Новость не найдена")
    })
    @GetMapping("/news/{newsId}")
    public ResponseEntity<List<CommentDTO.Response>> findByNewsId(@PathVariable Long newsId) {
        List<CommentDTO.Response> comments = commentService.findByNewsId(newsId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Обновить комментарий", description = "Обновляет текст комментария. Только владелец может обновить")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для обновления")
    })
    @PutMapping("/{id}")
    @CheckOwnership(entityType = "comment", idParam = "id")
    public ResponseEntity<CommentDTO.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody CommentDTO.Update commentDto) {
        CommentDTO.Response response = commentService.update(id, commentDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Удалить комментарий", description = "Удаляет комментарий по ID. Только владелец может удалить")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Комментарий успешно удален"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найдена"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для удаления")
    })
    @DeleteMapping("/{id}")
    @CheckOwnership(entityType = "comment", idParam = "id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
