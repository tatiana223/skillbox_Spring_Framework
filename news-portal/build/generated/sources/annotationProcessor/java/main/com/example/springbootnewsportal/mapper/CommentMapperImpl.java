package com.example.springbootnewsportal.mapper;

import com.example.springbootnewsportal.dto.CommentDTO;
import com.example.springbootnewsportal.dto.UserDTO;
import com.example.springbootnewsportal.entity.Comment;
import com.example.springbootnewsportal.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T19:28:51+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.0.1 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment toEntity(CommentDTO.Create dto) {
        if ( dto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setContent( dto.getContent() );

        return comment;
    }

    @Override
    public CommentDTO.Response toResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO.Response response = new CommentDTO.Response();

        response.setId( comment.getId() );
        response.setContent( comment.getContent() );
        response.setAuthor( userToResponse( comment.getAuthor() ) );

        return response;
    }

    @Override
    public void updateEntity(CommentDTO.Update dto, Comment comment) {
        if ( dto == null ) {
            return;
        }

        comment.setContent( dto.getContent() );
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
}
