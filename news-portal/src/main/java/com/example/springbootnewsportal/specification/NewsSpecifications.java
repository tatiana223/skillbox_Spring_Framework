package com.example.springbootnewsportal.specification;

import com.example.springbootnewsportal.entity.News;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class NewsSpecifications {

    public static Specification<News> withFilters(Long categoryId, Long authorId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            if (authorId != null)  {
                predicates.add(criteriaBuilder.equal(root.get("author").get("id"), authorId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };


    }
}
