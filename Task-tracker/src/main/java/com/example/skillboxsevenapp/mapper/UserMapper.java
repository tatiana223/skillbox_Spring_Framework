package com.example.skillboxsevenapp.mapper;

import com.example.skillboxsevenapp.dto.UserRequestDto;
import com.example.skillboxsevenapp.dto.UserResponseDto;
import com.example.skillboxsevenapp.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto dto);
    UserResponseDto toResponseDto(User user);
}
