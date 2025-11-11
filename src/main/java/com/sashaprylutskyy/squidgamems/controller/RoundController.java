package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundListResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundResponseDTO;
import com.sashaprylutskyy.squidgamems.model.mapper.RoundMapper;
import com.sashaprylutskyy.squidgamems.service.RoundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/round")
public class RoundController {

    private final RoundService roundService;
    private final RoundMapper roundMapper;

    public RoundController(RoundService roundService, RoundMapper roundMapper) {
        this.roundService = roundService;
        this.roundMapper = roundMapper;
    }

    @PostMapping
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<RoundListResponseDTO> addRounds(@Validated @RequestBody RoundRequestDTO dto) {
        RoundListResponseDTO response = roundService.addRounds(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{competitionId}/current_round")
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<RoundResponseDTO> getCurrentRound(@PathVariable Long competitionId) {
        RoundResponseDTO response = roundService.getCurrentRoundInfo(competitionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{competitionId}/next_round")
    @Secured("ROLE_FRONTMAN")
    public ResponseEntity<RoundResponseDTO> getNextRound(@PathVariable Long competitionId) {
        Round nextRound = roundService.getNextRound(competitionId);
        RoundResponseDTO response = roundMapper.toResponseDTO(nextRound);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{competitionId}/next_round/start")
    @Secured("ROLE_FRONTMAN")
    public ResponseEntity<RoundResponseDTO> startNextRound(@PathVariable Long competitionId) {
        RoundResponseDTO response = roundService.startNextRound(competitionId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{competitionId}/current_round/end")
    @Secured("ROLE_FRONTMAN")
    public ResponseEntity<RoundResponseDTO> endCurrentRound(@PathVariable Long competitionId) {
        RoundResponseDTO response = roundService.endRound(competitionId);
        return ResponseEntity.ok(response);
    }
//
//    @PutMapping("/{roundId}/pause")
//    @Secured("ROLE_FRONTMAN")

}
