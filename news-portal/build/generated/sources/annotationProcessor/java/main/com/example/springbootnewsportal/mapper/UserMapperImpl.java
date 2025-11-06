package com.example.springbootnewsportal.mapper;

import com.example.springbootnewsportal.dto.UserDTO;
import com.example.springbootnewsportal.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T19:28:51+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDTO.Create dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.getUsername() );
        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );

        return user;
    }

    @Override
    public UserDTO.Response toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.Response response = new UserDTO.Response();

        response.setId( user.getId() );
        response.setUsername( user.getUsername() );
        response.setEmail( user.getEmail() );

        return response;
    }

    @Override
    public void updateEntity(UserDTO.Update dto, User user) {
        if ( dto == null ) {
            return;
        }

        user.setUsername( dto.getUsername() );
        user.setEmail( dto.getEmail() );
    }
}
