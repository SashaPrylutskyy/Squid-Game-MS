package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Competition;
import com.sashaprylutskyy.squidgamems.model.Game;
import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundListResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundSummaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring")
public interface RoundMapper {

    @Mappings({
            @Mapping(target = "competition", source = "competition"),
            @Mapping(target = "game", source = "game"),
            @Mapping(target = "roundNumber", source = "roundNumber"),
            @Mapping(target = "status", constant = "PENDING"),
            @Mapping(target = "startedAt", ignore = true),
            @Mapping(target = "endedAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Round toEntity(Competition competition, Game game, Byte roundNumber);

    default List<Round> toEntityList(
            Competition competition,
            List<Game> games
    ) {
        return IntStream.range(0, games.size())
                .mapToObj(i -> toEntity(
                        competition,
                        games.get(i),
                        (byte) (i + 1)
                ))
                .toList();
    }

    @Mappings({
            @Mapping(target = "gameId", source = "game.id"),
            @Mapping(target = "roundNumber", source = "roundNumber")
    })
    RoundSummaryDTO toSummaryDTO(Round round);

    default RoundListResponseDTO toListResponseDTO(Long competitionId, List<Round> rounds) {
        RoundListResponseDTO dto = new RoundListResponseDTO();
        dto.setCompetitionId(competitionId);
        dto.setRoundSummaryDTOs(
                rounds.stream()
                        .map(this::toSummaryDTO)
                        .toList()
        );
        return dto;
    }

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "game.gameTitle"),
            @Mapping(target = "competitionId", source = "competition.id"),
            @Mapping(target = "roundNumber", source = "roundNumber"),
            @Mapping(target = "gameId", source = "game.id"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "startedAt", source = "startedAt"),
            @Mapping(target = "endedAt", source = "endedAt")
    })
    RoundResponseDTO toResponseDTO(Round round);

    List<RoundResponseDTO> toResponseDTOList(List<Round> rounds);
}