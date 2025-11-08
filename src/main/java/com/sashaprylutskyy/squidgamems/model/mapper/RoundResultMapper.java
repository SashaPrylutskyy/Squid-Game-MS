package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.RoundResult;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoundResultMapper {

    @Mapping(target = "roundId", source = "round.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "time", source = "reportedAt")
    RoundResultResponseDTO toResponseDTO(RoundResult roundResult);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "status", source = "status")
    UserSummaryDTO roundResultToUserSummaryDTO(RoundResult roundResult);


    default RoundResultSummaryDTO toSummaryDTO(List<RoundResult> rrs) {
        if (rrs == null || rrs.isEmpty()) {
            return null;
        }

        RoundResultSummaryDTO summaryDTO = new RoundResultSummaryDTO();

        summaryDTO.setRoundId(rrs.get(0).getRound().getId());

        List<UserSummaryDTO> players = rrs.stream()
                .map(this::roundResultToUserSummaryDTO)
                .collect(Collectors.toList());

        summaryDTO.setPlayers(players);

        return summaryDTO;
    }
}