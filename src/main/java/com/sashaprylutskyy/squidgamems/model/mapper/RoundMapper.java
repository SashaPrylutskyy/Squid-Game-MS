package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundSummaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoundMapper {

    @Mappings({
        @Mapping(target = "competitionId", source = "competitionId"),
        @Mapping(target = "gameId", source = "dto.gameId"),
        @Mapping(target = "roundNumber", source = "dto.roundNumber"),
        @Mapping(target = "status", constant = "CONFIGURING"),
        @Mapping(target = "startedAt", ignore = true),
        @Mapping(target = "endedAt", ignore = true),
        @Mapping(target = "id", ignore = true)
    })
    Round toEntity(Long competitionId, RoundSummaryDTO dto);

    default List<Round> toEntityList(RoundRequestDTO request) {
        return request.getRoundSummaryDTOs()
                .stream()
                .map(dto -> toEntity(request.getCompetitionId(), dto))
                .toList();
    }

    @Mappings({
            @Mapping(target = "gameId", source = "gameId"),
            @Mapping(target = "roundNumber", source = "roundNumber")
    })
    RoundSummaryDTO toSummaryDTO(Round round);

    default RoundResponseDTO toResponseDTO(Long competitionId, List<Round> rounds) {
        RoundResponseDTO dto = new RoundResponseDTO();
        dto.setCompetitionId(competitionId);
        dto.setRoundSummaryDTOs(
                rounds.stream()
                        .map(this::toSummaryDTO)
                        .toList()
        );
        return dto;
    }
}
