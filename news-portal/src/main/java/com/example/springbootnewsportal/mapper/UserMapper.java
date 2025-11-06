package com.example.springbootnewsportal.mapper;

import com.example.springbootnewsportal.dto.UserDTO;
import com.example.springbootnewsportal.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO.Create dto);

    UserDTO.Response toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    void updateEntity(UserDTO.Update dto, @MappingTarget User user);


}
