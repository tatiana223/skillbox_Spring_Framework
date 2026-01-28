package com.example.skillboxsevenapp.mapper;

import com.example.skillboxsevenapp.dto.TaskRequestDto;
import com.example.skillboxsevenapp.dto.TaskResponseDto;
import com.example.skillboxsevenapp.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "observers", ignore = true)
    Task toEntity(TaskRequestDto dto);
    TaskResponseDto toResponseDto(Task task);
}
