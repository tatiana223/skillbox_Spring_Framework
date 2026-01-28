package com.example.springbootnewsportal.service;

import com.example.springbootnewsportal.entity.Category;
import com.example.springbootnewsportal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category findOrCreateCategory(String categoryName) {
        log.debug("Поиск или создание категории: {}", categoryName);

        return categoryRepository.findByName(categoryName)
                .orElseGet(
                        () -> {
                            log.info("Создание новой категории: {}", categoryName);
                            Category newCategory = new Category();
                            newCategory.setName(categoryName);
                            return categoryRepository.save(newCategory);
                        }
                );
    }
}
