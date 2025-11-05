package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

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

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "status", source = "user.status")
    UserSummaryDTO toSummaryDTO(Assignment assignment);

    default List<UserSummaryDTO> toSummaryDTOList(List<Assignment> assignments) {
        if (assignments == null) {
            return List.of();
        }
        return assignments.stream()
                .map(this::toSummaryDTO)
                .collect(Collectors.toList());
    }
}
