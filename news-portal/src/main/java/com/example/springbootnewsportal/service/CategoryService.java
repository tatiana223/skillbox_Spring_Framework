package com.example.springbootnewsportal.service;

import com.example.springbootnewsportal.dto.CategoryDTO;
import com.example.springbootnewsportal.entity.Category;
import com.example.springbootnewsportal.mapper.CategoryMapper;
import com.example.springbootnewsportal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDTO.Response create(CategoryDTO.Create categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new RuntimeException("Category with this name already exists");
        }

        Category category = categoryMapper.toEntity(categoryDto);
        Category savecategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savecategory);
    }

    public Page<CategoryDTO.Response> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponse);
    }

    public CategoryDTO.Response findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        return categoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryDTO.Response update(Long id, CategoryDTO.Update categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        categoryMapper.updateFromDto(categoryDto, category);
        Category updateCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updateCategory);
    }

    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (!category.getNews().isEmpty()) {
            throw new RuntimeException("Cannot delete category with associated news");
        }
        categoryRepository.delete(category);

    }

    public CategoryDTO.Response findByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + name));

        return categoryMapper.toResponse(category);
    }


}
