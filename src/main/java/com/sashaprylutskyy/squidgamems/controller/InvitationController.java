package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.InvitationRequestDTO;
import com.sashaprylutskyy.squidgamems.model.Invitation;
import com.sashaprylutskyy.squidgamems.service.InvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN", "ROLE_MANAGER", "ROLE_THE_OFFICER"})
    public ResponseEntity<Invitation> invite(@RequestBody @Validated InvitationRequestDTO dto) {
        Invitation invitation = invitationService.create(dto);
        return new ResponseEntity<>(invitation, HttpStatus.CREATED);
    }
}
