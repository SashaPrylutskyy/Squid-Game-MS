package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.WorkerAssignmentResponseDTO;
import com.sashaprylutskyy.squidgamems.model.enums.Role;
import com.sashaprylutskyy.squidgamems.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN"})
    public ResponseEntity<List<UserSummaryDTO>> getUsers(
            @RequestParam Role role,
            @RequestParam(required = false) Boolean isAssigned
    ) {
        List<UserSummaryDTO> users = userService.getUsersByRole(role, isAssigned);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/worker/assignment")
    @Secured({"ROLE_WORKER", "ROLE_FRONTMAN"})
    public ResponseEntity<WorkerAssignmentResponseDTO> getWorkerAssignment() {
        WorkerAssignmentResponseDTO response = userService.getWorkerAssignment();
        return ResponseEntity.ok(response);
    }

}