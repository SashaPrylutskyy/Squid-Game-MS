package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Game;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.game.GameRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.game.GameSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {

    Game toEntity(GameRequestDTO dto);

    GameSummaryDTO toSummaryDTO(Game game);

    List<GameSummaryDTO> toSummaryDTOList(List<Game> games);

    UserSummaryDTO userEntityToSummaryDTO(User user);

}
