package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role", target = "roleTitle")
    UserResponseDTO toResponseDTO(User user);

    @Mapping(source = "role", target = "roleTitle")
    UserSummaryDTO toSummaryDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserRequestDTO dto);

    default String roleToString(Role role) {
        return role != null ? role.toString() : null;
    }
}
