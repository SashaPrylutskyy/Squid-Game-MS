package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.refCode.RefCodeSummaryDTO;
import com.sashaprylutskyy.squidgamems.service.RefCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ref-code")
public class RefCodeController {

    private final RefCodeService refCodeService;

    public RefCodeController(RefCodeService refCodeService) {
        this.refCodeService = refCodeService;
    }

    @GetMapping
    public ResponseEntity<RefCodeSummaryDTO> getRefCodeOfPrincipal() {
        RefCodeSummaryDTO summaryDTO = refCodeService.getRefCode();
        return new ResponseEntity<>(summaryDTO, HttpStatus.OK);
    }
}
