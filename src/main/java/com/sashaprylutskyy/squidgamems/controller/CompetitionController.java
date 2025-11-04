package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.competition.CompetitionResponseDTO;
import com.sashaprylutskyy.squidgamems.service.CompetitionService;
import com.sashaprylutskyy.squidgamems.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/competition")
public class CompetitionController {

    private final CompetitionService competitionService;
    private final UserService userService;

    public CompetitionController(CompetitionService competitionService, UserService userService) {
        this.competitionService = competitionService;
        this.userService = userService;
    }

    @PostMapping("/{title}")
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<CompetitionResponseDTO> create(@PathVariable String title) {
        User principal = userService.getPrincipal();
        CompetitionResponseDTO dto = competitionService.create(title, principal);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
