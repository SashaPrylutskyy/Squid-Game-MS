package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    AssignmentResponseDTO toResponseDTO(Assignment assignment);

    @Mapping(source = "role", target = "roleTitle")
    UserSummaryDTO userToUserSummaryDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignedBy", ignore = true)
    Assignment toEntity(AssignmentRequestDTO dto);

    default String roleToString(Role role) {
        return role != null ? role.toString() : null;
    }
}
