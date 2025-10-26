package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.refCode.RefCodeSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import com.sashaprylutskyy.squidgamems.service.RefCodeService;
import com.sashaprylutskyy.squidgamems.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ref-code")
public class RefCodeController {

    private final RefCodeService refCodeService;
    private final UserService userService;

    public RefCodeController(RefCodeService refCodeService, UserService userService) {
        this.refCodeService = refCodeService;
        this.userService = userService;
    }

    @GetMapping
    @Secured({"ROLE_SALESMAN"})
    public ResponseEntity<RefCodeSummaryDTO> getRefCodeOfPrincipal() {
        RefCodeSummaryDTO summaryDTO = refCodeService.getRefCode();
        return new ResponseEntity<>(summaryDTO, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<UserSummaryDTO> joinAGame() {
        UserSummaryDTO player = userService.registerPlayer();
        return new ResponseEntity<>(player, HttpStatus.CREATED);
    }
}
