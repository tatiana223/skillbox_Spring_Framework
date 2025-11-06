package com.example.springbootnewsportal.mapper;

import com.example.springbootnewsportal.dto.CommentDTO;
import com.example.springbootnewsportal.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toEntity(CommentDTO.Create dto);

    CommentDTO.Response toResponse(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "news", ignore = true)
    void updateEntity(CommentDTO.Update dto, @MappingTarget Comment comment);
}
