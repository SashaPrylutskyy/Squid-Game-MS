package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.vote.VoteResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.vote.VoteResultDTO;
import com.sashaprylutskyy.squidgamems.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voting")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    //todo: voted, not voted

    @GetMapping("/{roundId}")
    @Secured({"ROLE_FRONTMAN", "ROLE_HOST"})
    public ResponseEntity<List<VoteResponseDTO>> getVotes(@PathVariable Long roundId) {
        List<VoteResponseDTO> response = voteService.getVotes(roundId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roundId}/{isQuit}")
    @Secured({"ROLE_FRONTMAN", "ROLE_HOST"})
    public ResponseEntity<List<VoteResponseDTO>> getVotes(@PathVariable Long roundId,
                                                          @PathVariable boolean isQuit) {
        List<VoteResponseDTO> response = voteService.getVotes(roundId, isQuit);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{roundId}/vote/{isQuit}")
    @Secured("ROLE_PLAYER")
    public ResponseEntity<VoteResponseDTO> vote(@PathVariable Long roundId,
                                                @PathVariable boolean isQuit) {
        VoteResponseDTO response = voteService.vote(roundId, isQuit);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{roundId}/results")
    @Secured("ROLE_FRONTMAN")
    public ResponseEntity<VoteResultDTO> getResults(@PathVariable Long roundId) {
        VoteResultDTO results = voteService.getResults(roundId);
        return ResponseEntity.ok(results);
    }

}
