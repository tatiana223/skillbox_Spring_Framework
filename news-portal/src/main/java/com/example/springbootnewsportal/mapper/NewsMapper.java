package com.example.springbootnewsportal.mapper;

import com.example.springbootnewsportal.dto.NewsDTO;
import com.example.springbootnewsportal.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsDTO.Response toResponse(News news);

    News toEntity(NewsDTO.Create dto);
    NewsDTO.DetailedResponse toDetailedResponse(News news);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void updateEntity(NewsDTO.Update dto, @MappingTarget News news);

}
