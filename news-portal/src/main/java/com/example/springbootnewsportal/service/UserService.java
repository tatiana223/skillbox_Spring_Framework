package com.example.springbootnewsportal.service;

import com.example.springbootnewsportal.dto.UserDTO;
import com.example.springbootnewsportal.entity.User;
import com.example.springbootnewsportal.mapper.UserMapper;
import com.example.springbootnewsportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO.Response create(UserDTO.Create userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(userDto);
        User saveUser = userRepository.save(user);
        return userMapper.toResponse(saveUser);
    }

    public Page<UserDTO.Response> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    public UserDTO.Response findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserDTO.Response updateById(Long id, UserDTO.Update userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        userMapper.updateEntity(userDto, user);
        User updateUser = userRepository.save(user);
        return userMapper.toResponse(updateUser);

    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    
}
