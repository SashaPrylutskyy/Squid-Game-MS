package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.JobOffer;
import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferResponseDTO;
import com.sashaprylutskyy.squidgamems.model.enums.JobOfferStatus;
import com.sashaprylutskyy.squidgamems.model.mapper.JobOfferMapper;
import com.sashaprylutskyy.squidgamems.repository.JobOfferRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobOfferService {

    private final JobOfferRepository jobOfferRepo;
    private final UserService userService;
    private final RoleService roleService;
    private final JobOfferMapper jobOfferMapper;


    public JobOfferService(JobOfferRepository jobOfferRepo, UserService userService,
                           RoleService roleService, JobOfferMapper jobOfferMapper) {
        this.jobOfferRepo = jobOfferRepo;
        this.userService = userService;
        this.roleService = roleService;
        this.jobOfferMapper = jobOfferMapper;
    }

    //todo implement invitation for other then HOST. For the time being this is a problem cuz it's difficult to catch to which lobby does a person belongs to
    public JobOfferResponseDTO makeJobOffer(JobOfferRequestDTO dto) {
        User principal = userService.getPrincipal();
        Role role = roleService.getRoleById(dto.getRoleId());

        if (role.toString().equals("HOST") || role.toString().equals("VIP") || role.toString().equals("PLAYER")) {
            throw new RuntimeException("Unable to make a job offer neither for VIP nor HOST nor PLAYER");
        }

        JobOffer jobOffer = new JobOffer(
                principal.getId(),
                principal,
                role,
                dto.getEmail(),
                JobOfferStatus.AWAITING,
                System.currentTimeMillis()
        );
        jobOffer = jobOfferRepo.save(jobOffer);
        return jobOfferMapper.toResponseDTO(jobOffer);
    }
}
