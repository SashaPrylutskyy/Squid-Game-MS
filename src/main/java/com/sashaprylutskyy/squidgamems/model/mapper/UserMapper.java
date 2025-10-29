package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toResponseDTO(User user);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "balance", ignore = true)
    UserRequestDTO toUserRequestDTO(JobOfferRequestUserDTO dto);

    @Mapping(target = "role", ignore = true)
    UserRequestDTO toUserRequestDTO(UserRequestPlayerDTO dto);

    UserSummaryDTO toSummaryDTO(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDTO dto);

}
