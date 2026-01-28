package com.example.springbootnewsportal.controller;

import com.example.springbootnewsportal.dto.BookDto;
import com.example.springbootnewsportal.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity createBook(@RequestBody BookDto bookDto) {
        try {
            String result = bookService.createBook(bookDto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<String> getBookByTitleAndAuthor(
            @RequestParam String title,
            @RequestParam String author) {

        try {
            String result = bookService.findByTitleAndAuthor(title, author);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<String> getBooksByCategory(@PathVariable String categoryName) {
        try {
            String result = bookService.findByCategoryName(categoryName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(
            @PathVariable Long id,
            @RequestBody BookDto bookDTO) {

        try {
            return ResponseEntity.ok("🔄 Обновление книги (в разработке)");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            String result = bookService.deleteBook(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearAllBooks() {
        try {
            String result = bookService.clearAllBooks();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @PostMapping("/cache/clear")
    public ResponseEntity<String> clearAllCaches() {
        try {
            bookService.evictAllCaches();
            return ResponseEntity.ok("Все кеши очищены");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

}
