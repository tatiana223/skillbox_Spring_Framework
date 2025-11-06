package com.example.springbootnewsportal.mapper;

import com.example.springbootnewsportal.dto.CategoryDTO;
import com.example.springbootnewsportal.dto.CommentDTO;
import com.example.springbootnewsportal.dto.NewsDTO;
import com.example.springbootnewsportal.dto.UserDTO;
import com.example.springbootnewsportal.entity.Category;
import com.example.springbootnewsportal.entity.Comment;
import com.example.springbootnewsportal.entity.News;
import com.example.springbootnewsportal.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T19:28:51+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.0.1 (Oracle Corporation)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsDTO.Response toResponse(News news) {
        if ( news == null ) {
            return null;
        }

        NewsDTO.Response response = new NewsDTO.Response();

        response.setId( news.getId() );
        response.setTitle( news.getTitle() );
        response.setContent( news.getContent() );
        response.setAuthor( userToResponse( news.getAuthor() ) );
        response.setCategory( categoryToResponse( news.getCategory() ) );

        return response;
    }

    @Override
    public News toEntity(NewsDTO.Create dto) {
        if ( dto == null ) {
            return null;
        }

        News news = new News();

        news.setTitle( dto.getTitle() );
        news.setContent( dto.getContent() );

        return news;
    }

    @Override
    public NewsDTO.DetailedResponse toDetailedResponse(News news) {
        if ( news == null ) {
            return null;
        }

        NewsDTO.DetailedResponse detailedResponse = new NewsDTO.DetailedResponse();

        detailedResponse.setId( news.getId() );
        detailedResponse.setTitle( news.getTitle() );
        detailedResponse.setContent( news.getContent() );
        detailedResponse.setAuthor( userToResponse( news.getAuthor() ) );
        detailedResponse.setCategory( categoryToResponse( news.getCategory() ) );
        detailedResponse.setComments( commentListToResponseList( news.getComments() ) );

        return detailedResponse;
    }

    @Override
    public void updateEntity(NewsDTO.Update dto, News news) {
        if ( dto == null ) {
            return;
        }

        news.setTitle( dto.getTitle() );
        news.setContent( dto.getContent() );
    }

    protected UserDTO.Response userToResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.Response response = new UserDTO.Response();

        response.setId( user.getId() );
        response.setUsername( user.getUsername() );
        response.setEmail( user.getEmail() );

        return response;
    }

    protected CategoryDTO.Response categoryToResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO.Response response = new CategoryDTO.Response();

        response.setId( category.getId() );
        response.setName( category.getName() );
        response.setDescription( category.getDescription() );

        return response;
    }

    protected CommentDTO.Response commentToResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO.Response response = new CommentDTO.Response();

        response.setId( comment.getId() );
        response.setContent( comment.getContent() );
        response.setAuthor( userToResponse( comment.getAuthor() ) );

        return response;
    }

    protected List<CommentDTO.Response> commentListToResponseList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDTO.Response> list1 = new ArrayList<CommentDTO.Response>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentToResponse( comment ) );
        }

        return list1;
    }
}
