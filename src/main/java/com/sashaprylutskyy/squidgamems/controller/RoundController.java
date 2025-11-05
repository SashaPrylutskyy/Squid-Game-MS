package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.round.RoundRequestDTO;
import com.sashaprylutskyy.squidgamems.service.RoundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/round")
public class RoundController {

    private final RoundService roundService;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @PostMapping
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<?> createRounds(@Validated @RequestBody RoundRequestDTO dto) {
        roundService.createRounds(dto);
        return new ResponseEntity<>("DONE", HttpStatus.CREATED);
    }
}
