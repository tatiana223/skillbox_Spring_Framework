package com.example.springbootnewsportal.mapper;

import com.example.springbootnewsportal.dto.CategoryDTO;
import com.example.springbootnewsportal.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T19:28:51+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDTO.Response toResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO.Response response = new CategoryDTO.Response();

        response.setId( category.getId() );
        response.setName( category.getName() );
        response.setDescription( category.getDescription() );

        return response;
    }

    @Override
    public Category toEntity(CategoryDTO.Create dto) {
        if ( dto == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( dto.getName() );
        category.setDescription( dto.getDescription() );

        return category;
    }

    @Override
    public void updateFromDto(CategoryDTO.Update dto, Category category) {
        if ( dto == null ) {
            return;
        }

        category.setName( dto.getName() );
        category.setDescription( dto.getDescription() );
    }
}
