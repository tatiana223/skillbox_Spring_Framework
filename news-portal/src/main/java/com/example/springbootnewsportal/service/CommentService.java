package com.example.springbootnewsportal.service;

import com.example.springbootnewsportal.dto.CommentDTO;
import com.example.springbootnewsportal.entity.Comment;
import com.example.springbootnewsportal.entity.News;
import com.example.springbootnewsportal.entity.User;
import com.example.springbootnewsportal.mapper.CommentMapper;
import com.example.springbootnewsportal.repository.CommentRepository;
import com.example.springbootnewsportal.repository.NewsRepository;
import com.example.springbootnewsportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    public CommentDTO.Response create(CommentDTO.Create commentDto, String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        News news = newsRepository.findById(commentDto.getNewsId())
                .orElseThrow(() -> new RuntimeException("News not found"));

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setAuthor(user);
        comment.setNews(news);

        Comment savesComment = commentRepository.save(comment);
        return commentMapper.toResponse(savesComment);

    }

    @Transactional(readOnly = true)
    public List<CommentDTO.Response> findByNewsId(Long newsId) {
        List<Comment> comments = commentRepository.findByNewsIdOrderByCreateAtDesc(newsId);
        return comments.stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO.Response update(Long id, CommentDTO.Update commentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));

        commentMapper.updateEntity(commentDto, comment);
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toResponse(updatedComment);
    }

    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        commentRepository.delete(comment);
    }

    public List<CommentDTO.Response> findByAuthorId(Long authorId) {
        List<Comment> comments = commentRepository.findByAuthorId(authorId);
        return comments.stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentDTO.Response findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        return commentMapper.toResponse(comment);
    }
}
