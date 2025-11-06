package com.example.springbootnewsportal.mapper;

import com.example.springbootnewsportal.dto.CategoryDTO;
import com.example.springbootnewsportal.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO.Response toResponse(Category category);

    Category toEntity(CategoryDTO.Create dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(CategoryDTO.Update dto, @MappingTarget Category category);

}
