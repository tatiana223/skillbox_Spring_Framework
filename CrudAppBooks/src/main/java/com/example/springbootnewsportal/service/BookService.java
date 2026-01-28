package com.example.springbootnewsportal.service;

import com.example.springbootnewsportal.config.AppCacheProperties;
import com.example.springbootnewsportal.dto.BookDto;
import com.example.springbootnewsportal.entity.Book;
import com.example.springbootnewsportal.entity.Category;
import com.example.springbootnewsportal.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY,
                    key = "#bookDTO?.categoryName != null ? #bookDTO.categoryName : ''",
                    beforeInvocation = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR,
                    key = "(#bookDTO?.title != null ? #bookDTO.title : '') + '|' + (#bookDTO?.author != null ? #bookDTO.author : '')",
                    beforeInvocation = true)
    })
    @Transactional
    public String createBook(BookDto bookDto) {
        log.info("Создаем книгу: {}", bookDto.getTitle());

        Category category = categoryService.findOrCreateCategory(bookDto.getCategoryName());


        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setCategory(category);

        Book saveBook = bookRepository.save(book);

        return String.format(
                "Книга создана! ID: %s, Название: '%s', Автор: '%s', Категория: '%s'",
                saveBook.getId(), saveBook.getTitle(), saveBook.getAuthor(), category.getName()
        );
    }

    @Cacheable(value = "bookByTitleAndAuthor", key = "#title + '|' + #author")
    @Transactional
    public String findByTitleAndAuthor(String title, String author) {
        log.info("Поиск книги: title='{}', author='{}'", title, author);

        Book book = bookRepository.findByTitleAndAuthor(title, author)
                .orElseThrow(() -> new RuntimeException("Книга не найдена: " + title + " - " + author));

        return "Найдена книга: ID: " + book.getId() +
                ", Название: '" + book.getTitle() +
                "', Автор: '" + book.getAuthor() +
                "', ISBN: '" + book.getIsbn() +
                "', Год: " + book.getPublicationYear() +
                ", Категория: '" + book.getCategory().getName() + "'";
    }

    @Cacheable(value = "booksByCategory", key = "#categoryName")
    @Transactional(readOnly = true)
    public String findByCategoryName(String categoryName) {
        log.info("Поиск книг по категории: {}", categoryName);

        List<Book> books = bookRepository.findByCategoryName(categoryName);

        if (books.isEmpty()) {
            return "В категории '" + categoryName + "' книг не найдено";
        }

        StringBuilder result = new StringBuilder();
        result.append("Книги в категории: ").append(categoryName).append("':\n'");

        for (Book book : books) {
            result.append(" - ").append(book.getTitle())
                    .append(" (").append(book.getAuthor()).append(")\n");

        }
        result.append("Всего найдено: ").append(books.size()).append(" книг");

        return result.toString();
    }

    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY, allEntries = true, beforeInvocation = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR, allEntries = true, beforeInvocation = true)
    })
    @Transactional
    public String updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с ID: " + id));

        String oldTitle = book.getTitle();
        String oldAuthor = book.getAuthor();
        String oldCategoryName = book.getCategory().getName();

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setPublicationYear(bookDto.getPublicationYear());

        if (!oldCategoryName.equals(bookDto.getCategoryName())) {
            Category category = categoryService.findOrCreateCategory(bookDto.getCategoryName());
            book.setCategory(category);
        }

        Book updatedBook = bookRepository.save(book);

        if (!oldCategoryName.equals(bookDto.getCategoryName())) {
            evictBooksByCategoryCache(oldCategoryName);
        }

        if (!oldTitle.equals(bookDto.getTitle()) || !oldAuthor.equals(bookDto.getAuthor())) {
            evictBookByTitleAndAuthorCache(oldTitle, oldAuthor);
        }

        return "Книга обновлена! ID: " + updatedBook.getId() +
                ", Название: '" + updatedBook.getTitle() +
                "', Автор: '" + updatedBook.getAuthor() +
                "', Категория: '" + book.getCategory().getName() + "'";

    }

    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY, allEntries = true, beforeInvocation = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR, allEntries = true, beforeInvocation = true)
    })
    @Transactional
    public String deleteBook(Long id) {
        log.info("Удаление книги ID: {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с ID: " + id));

        bookRepository.delete(book);

        String bookInfo = book.getTitle() + " - " + book.getAuthor();

        log.info("Книга удалена: {}", bookInfo);
        return "Книга удалена: " + bookInfo;
    }

    @Transactional
    public String clearAllBooks() {
        Long count = bookRepository.count();

        bookRepository.deleteAll();

        evictAllCaches();

        return "Удалено всех книг: " + count;
    }

    @CacheEvict(value = {AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR, AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY}, allEntries = true)
    public void evictAllCaches() {
        log.info("Инвалидированы все кеши");
    }

    @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR, key = "#title + '|' + #author", beforeInvocation = true)
    public void evictBookByTitleAndAuthorCache(String title, String author) {
        log.info("Инвалидация кеша для книги: '{}' - '{}'", title, author);
    }

    @CacheEvict(value = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY, key = "#categoryName", beforeInvocation = true)
    public void evictBooksByCategoryCache(String categoryName) {
        log.info("Инвалидация кеша для категории: '{}'", categoryName);
    }


}
