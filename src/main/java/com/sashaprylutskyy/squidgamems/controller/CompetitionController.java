package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.competition.CompetitionResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.enums.Env;
import com.sashaprylutskyy.squidgamems.model.enums.Sex;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import com.sashaprylutskyy.squidgamems.service.CompetitionService;
import com.sashaprylutskyy.squidgamems.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{competitionId}")
    public ResponseEntity<List<UserSummaryDTO>> getUsers(@PathVariable Long competitionId) {
        List<UserSummaryDTO> alivePlayers = userService.getListOfUsers(Env.COMPETITION, competitionId);
        return new ResponseEntity<>(alivePlayers, HttpStatus.OK);
    }

    @GetMapping("/{competitionId}/{userStatus}")
    public ResponseEntity<List<UserSummaryDTO>> getUsers(@PathVariable Long competitionId,
                                                         @PathVariable UserStatus userStatus) {
        List<UserSummaryDTO> alivePlayers = userService
                .getListOfUsers(Env.COMPETITION, competitionId, userStatus);
        return new ResponseEntity<>(alivePlayers, HttpStatus.OK);
    }

    @GetMapping("/{competitionId}/{userStatus}/{sex}")
    public ResponseEntity<List<UserSummaryDTO>> getUsers(@PathVariable Long competitionId,
                                                         @PathVariable UserStatus userStatus,
                                                         @PathVariable Sex sex) {
        List<UserSummaryDTO> alivePlayers = userService
                .getListOfUsers(Env.COMPETITION, competitionId, userStatus, sex);
        return new ResponseEntity<>(alivePlayers, HttpStatus.OK);
    }

}
