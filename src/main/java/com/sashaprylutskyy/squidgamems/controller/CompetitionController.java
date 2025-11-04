package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.competition.CompetitionResponseDTO;
import com.sashaprylutskyy.squidgamems.service.CompetitionService;
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

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping("/{title}")
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<CompetitionResponseDTO> create(@PathVariable String title) {
        CompetitionResponseDTO dto = competitionService.create(title);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
