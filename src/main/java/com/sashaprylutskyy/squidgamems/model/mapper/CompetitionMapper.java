package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Competition;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.competition.CompetitionResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.competition.CompetitionSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    CompetitionResponseDTO toResponseDTO(Competition competition);

    UserSummaryDTO userEntityToSummaryDTO(User user);

    CompetitionSummaryDTO toSummaryDTO(Competition competition);

}
