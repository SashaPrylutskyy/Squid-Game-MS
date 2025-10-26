package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.refCode.RefCodeSummaryDTO;
import com.sashaprylutskyy.squidgamems.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ref-code")
public class RefCodeController {

    private final UserService userService;

    public RefCodeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Secured({"ROLE_SALESMAN"})
    public ResponseEntity<RefCodeSummaryDTO> getMyRefcode() {
        RefCodeSummaryDTO summaryDTO = userService.getMyRefCode();
        return new ResponseEntity<>(summaryDTO, HttpStatus.OK);
    }

}
