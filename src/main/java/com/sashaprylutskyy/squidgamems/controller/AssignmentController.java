package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentResponseDTO;
import com.sashaprylutskyy.squidgamems.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<AssignmentResponseDTO> assignPlayersToCompetition(@RequestBody @Validated AssignmentRequestDTO dto) {
        AssignmentResponseDTO assignment = assignmentService.assignPlayersToCompetition(dto);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

}
