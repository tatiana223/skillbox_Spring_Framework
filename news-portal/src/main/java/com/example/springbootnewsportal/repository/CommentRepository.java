package com.example.springbootnewsportal.repository;

import com.example.springbootnewsportal.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByNewsId(Long newsId);
    List<Comment> findByAuthorId(Long authorId);
    List<Comment> findByNewsIdOrderByCreateAtDesc(Long newsId);
}
