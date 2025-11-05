package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Game;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.game.GameRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.game.GameSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.mapper.GameMapper;
import com.sashaprylutskyy.squidgamems.repository.GameRepo;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final UserService userService;
    private final GameMapper gameMapper;
    private final GameRepo gameRepo;

    public GameService(UserService userService, GameMapper gameMapper, GameRepo gameRepo) {
        this.userService = userService;
        this.gameMapper = gameMapper;
        this.gameRepo = gameRepo;
    }

    public GameSummaryDTO create(GameRequestDTO dto) {
        User principal = userService.getPrincipal();
        Long now = System.currentTimeMillis();

        Game game = gameMapper.toEntity(dto);
        game.setCreatedBy(principal);
        game.setCreatedAt(now);

        game = gameRepo.save(game);
        return gameMapper.toSummaryDTO(game);
    }

}
