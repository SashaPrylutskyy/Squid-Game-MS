package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Invitation;
import com.sashaprylutskyy.squidgamems.model.dto.invitation.InvitationResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvitationMapper {

    InvitationResponseDTO toResponseDTO(Invitation invitation);

}
