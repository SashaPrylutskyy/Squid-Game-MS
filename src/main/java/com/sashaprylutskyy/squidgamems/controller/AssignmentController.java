package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentRequestPlayersDTO;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentResponsePlayersDTO;
import com.sashaprylutskyy.squidgamems.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final UserService userService;

    public AssignmentController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<AssignmentResponsePlayersDTO> assignPlayersToCompetition(@RequestBody @Validated AssignmentRequestPlayersDTO dto) {
        AssignmentResponsePlayersDTO assignment = userService.assignPlayersToFromCompetition(true, dto);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

    @DeleteMapping
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<AssignmentResponsePlayersDTO> removePlayersFromCompetition(@RequestBody @Validated AssignmentRequestPlayersDTO dto) {
        AssignmentResponsePlayersDTO assignment = userService.assignPlayersToFromCompetition(false, dto);
        return new ResponseEntity<>(assignment, HttpStatus.OK);
    }

}
