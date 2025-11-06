package com.example.springbootnewsportal.aop;

import com.example.springbootnewsportal.entity.Comment;
import com.example.springbootnewsportal.entity.News;
import com.example.springbootnewsportal.entity.User;
import com.example.springbootnewsportal.repository.CommentRepository;
import com.example.springbootnewsportal.repository.NewsRepository;
import com.example.springbootnewsportal.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class OwnershipAspect {
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Before("@annotation(checkOwnership)")
    public void checkOwnership(CheckOwnership checkOwnership) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();


        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
        );


        String idParam = checkOwnership.idParam();
        Long entityId = Long.parseLong(pathVariables.get(idParam));


        String currentUsername = getCurrentUsername();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));


        switch (checkOwnership.entityType()) {
            case "news":
                checkNewsOwnership(entityId, currentUser);
                break;
            case "comment":
                checkCommentOwnership(entityId, currentUser);
                break;
            default:
                throw new IllegalArgumentException("Unknown entity type: " + checkOwnership.entityType());
        }

        log.info("Ownership check passed for {}: {}", checkOwnership.entityType(), entityId);
    }

    private void checkNewsOwnership(Long newsId, User currentUser) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + newsId));

        if (!news.getAuthor().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not the owner of this news");
        }
    }

    private void checkCommentOwnership(Long commentId, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        if (!comment.getAuthor().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not the owner of this comment");
        }
    }

    private String getCurrentUsername() {
        // Временная заглушка
        return "currentUser";
    }
}
