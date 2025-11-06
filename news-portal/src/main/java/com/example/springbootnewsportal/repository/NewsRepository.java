package com.example.springbootnewsportal.repository;

import com.example.springbootnewsportal.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    @Query("SELECT n FROM News n LEFT JOIN FETCH n.comments WHERE n.id = :id")
    Optional<News> findByIdWithComments(@Param("id") Long id);

    Page<News> findByCategoryId(Long categoryId, Pageable pageable);
    Page<News> findByAuthorId(Long authorId, Pageable pageable);

}
