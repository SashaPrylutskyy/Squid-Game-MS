package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Competition;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.competition.CompetitionResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.competition.CompetitionSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    CompetitionResponseDTO toResponseDTO(Competition competition);

    List<CompetitionResponseDTO> toResponseDTOList(List<Competition> competitions);

    UserSummaryDTO userEntityToSummaryDTO(User user);

    CompetitionSummaryDTO toSummaryDTO(Competition competition);

}
