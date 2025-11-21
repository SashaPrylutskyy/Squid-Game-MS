package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import com.sashaprylutskyy.squidgamems.service.RoundResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/round_result")
public class RoundResultController {

    private final RoundResultService roundResultService;

    public RoundResultController(RoundResultService roundResultService) {
        this.roundResultService = roundResultService;
    }

    @GetMapping("/{roundId}/reported")
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<RoundResultSummaryDTO> getReportedPlayers(@PathVariable Long roundId) {
        RoundResultSummaryDTO response = roundResultService.getReportsSummary(roundId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{roundId}/{playerId}/{userStatus}")
    @Secured({"ROLE_WORKER"})
    public ResponseEntity<RoundResultResponseDTO> reportPlayerResult(
            @PathVariable Long roundId,
            @PathVariable Long playerId,
            @PathVariable UserStatus userStatus) {
        RoundResultResponseDTO result = roundResultService
                .reportPlayerResult(roundId, playerId, userStatus);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/confirmation")
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<RoundResultSummaryDTO> confirmPlayerStatus(
            @Validated @RequestBody RoundResultRequestDTO dto) {
        RoundResultSummaryDTO response = roundResultService.confirmPlayerStatus(dto);
        return ResponseEntity.ok(response);
    }
}
