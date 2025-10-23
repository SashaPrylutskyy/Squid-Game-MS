package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.UserRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.UserResponseDTO;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        return new User(
                dto.getId(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getProfilePhoto(),
                dto.getSex(),
                dto.getBirthday(),
                dto.getBalance(),
                new Role(dto.getRoleId()),
                dto.getStatus()
        );
    }

    public static UserResponseDTO toResponse(User entity) {
        return new UserResponseDTO(
                entity.getId(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getProfilePhoto(),
                entity.getSex(),
                entity.getRole().toString(),
                entity.getBirthday(),
                entity.getBalance(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
