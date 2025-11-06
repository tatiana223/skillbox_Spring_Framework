package com.example.springbootnewsportal.service;

import com.example.springbootnewsportal.dto.NewsDTO;
import com.example.springbootnewsportal.entity.Category;
import com.example.springbootnewsportal.entity.News;
import com.example.springbootnewsportal.entity.User;
import com.example.springbootnewsportal.mapper.NewsMapper;
import com.example.springbootnewsportal.repository.CategoryRepository;
import com.example.springbootnewsportal.repository.NewsRepository;
import com.example.springbootnewsportal.repository.UserRepository;
import com.example.springbootnewsportal.specification.NewsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public NewsDTO.Response create(NewsDTO.Create newsDto, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User not found"));

        Category category = categoryRepository.findById(newsDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        News news = newsMapper.toEntity(newsDto);
        news.setAuthor(author);
        news.setCategory(category);

        News savedNews = newsRepository.save(news);
        return newsMapper.toResponse(savedNews);
    }


    @Transactional(readOnly = true)
    public Page<NewsDTO.Response> findAll(Long categoryId, Long authorId, Pageable pageable) {
        Specification<News> spec = NewsSpecifications.withFilters(categoryId, authorId);
        return newsRepository.findAll(spec, pageable)
                .map(newsMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public NewsDTO.DetailedResponse findById(Long id) {
        News news = newsRepository.findByIdWithComments(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
        return newsMapper.toDetailedResponse(news);
    }

    @Transactional
    public NewsDTO.Response update(Long id, NewsDTO.Update newsDto) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));

        if (news.getCategory() != null) {
            Category category = categoryRepository.findById(newsDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            news.setCategory(category);
        }

        newsMapper.updateEntity(newsDto, news);
        News updateNews = newsRepository.save(news);
        return newsMapper.toResponse(updateNews);
    }

    @Transactional
    public void delete(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
        newsRepository.delete(news);
    }

    public Page<NewsDTO.Response> findByCategoryId(Long categoryId, Pageable pageable) {
        return newsRepository.findByCategoryId(categoryId, pageable)
                .map(newsMapper::toResponse);
    }

    public Page<NewsDTO.Response> findByAuthorId(Long authorId, Pageable pageable) {
        return newsRepository.findByAuthorId(authorId, pageable)
                .map(newsMapper::toResponse);
    }


}
