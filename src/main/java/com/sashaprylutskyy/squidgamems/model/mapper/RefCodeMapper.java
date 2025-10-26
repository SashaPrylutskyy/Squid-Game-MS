package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.RefCode;
import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.refCode.RefCodeSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RefCodeMapper {

    RefCodeSummaryDTO toSummaryDTO(RefCode refCode);

    @Mapping(source = "role", target = "roleTitle")
    UserSummaryDTO userEntityToSummaryDTO(User user);

    default String roleToString(Role role) {
        return role != null ? role.toString() : null;
    }
}
