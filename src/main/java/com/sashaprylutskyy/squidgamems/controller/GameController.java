package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.game.GameRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.game.GameSummaryDTO;
import com.sashaprylutskyy.squidgamems.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<GameSummaryDTO> create(@Validated @RequestBody GameRequestDTO dto) {
        GameSummaryDTO game = gameService.create(dto);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }
}
