package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserRequestDTO;
import com.sashaprylutskyy.squidgamems.service.JobOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/job-offer")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @PostMapping
    @Secured({"ROLE_HOST", "ROLE_FRONTMAN", "ROLE_MANAGER", "ROLE_THE_OFFICER"})
    public ResponseEntity<JobOfferResponseDTO> makeJobOffer(@RequestBody @Validated JobOfferRequestDTO dto) {
        JobOfferResponseDTO jobOffer = jobOfferService.makeJobOffer(dto);
        return new ResponseEntity<>(jobOffer, HttpStatus.CREATED);
    }

    @PostMapping("/{token}/accept")
    public ResponseEntity<JobOfferSummaryDTO> acceptJobOffer
            (@PathVariable UUID token, @RequestBody @Validated UserRequestDTO dto) {
        JobOfferSummaryDTO jobOfferSummary = jobOfferService.acceptJobOffer(token, dto);
        return new ResponseEntity<>(jobOfferSummary, HttpStatus.ACCEPTED);
    }

}
