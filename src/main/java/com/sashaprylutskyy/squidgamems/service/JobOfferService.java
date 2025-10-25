package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.JobOffer;
import com.sashaprylutskyy.squidgamems.model.Lobby;
import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserRequestDTO;
import com.sashaprylutskyy.squidgamems.model.enums.JobOfferStatus;
import com.sashaprylutskyy.squidgamems.model.mapper.JobOfferMapper;
import com.sashaprylutskyy.squidgamems.repository.JobOfferRepository;
import com.sashaprylutskyy.squidgamems.repository.LobbyRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobOfferService {

    private final JobOfferRepository jobOfferRepo;
    private final UserService userService;
    private final RoleService roleService;
    private final JobOfferMapper jobOfferMapper;
    private final LobbyService lobbyService;


    public JobOfferService(JobOfferRepository jobOfferRepo, UserService userService,
                           RoleService roleService, JobOfferMapper jobOfferMapper,
                           LobbyService lobbyService) {
        this.jobOfferRepo = jobOfferRepo;
        this.userService = userService;
        this.roleService = roleService;
        this.jobOfferMapper = jobOfferMapper;
        this.lobbyService = lobbyService;
    }

    public JobOffer getOfferByToken(UUID token) {
        return jobOfferRepo.findByToken(token)
                .orElseThrow(() -> new NoResultException("Job offer %s isn't found".formatted(token)));
    }

    public JobOfferResponseDTO makeJobOffer(JobOfferRequestDTO dto) {
        User principal = userService.getPrincipal();
        Role role = roleService.getRoleById(dto.getRoleId());
        Lobby currentLobby = lobbyService.getLobbyByUserId(principal.getId());

        if (role.toString().equals("HOST") || role.toString().equals("VIP") || role.toString().equals("PLAYER")) {
            throw new RuntimeException("Unable to make a job offer neither for VIP nor HOST nor PLAYER");
        }

        JobOffer jobOffer = new JobOffer(
                currentLobby.getLobbyId(),
                principal,
                role,
                dto.getEmail(),
                JobOfferStatus.AWAITING,
                System.currentTimeMillis()
        );
        jobOffer = jobOfferRepo.save(jobOffer);
        return jobOfferMapper.toResponseDTO(jobOffer);
    }

    @Transactional
    public JobOfferSummaryDTO acceptJobOffer(UUID token, UserRequestDTO dto) {
        JobOffer offer = getOfferByToken(token);

        if (offer.getOfferStatus() != JobOfferStatus.AWAITING) {
            throw new IllegalStateException("Job offer is " + offer.getOfferStatus().name());
        }
        if (!offer.getEmail().equals(dto.getEmail())) {
            throw new SecurityException("Email in DTO does not match the invited email.");
        }
        User newUser = userService.createUserFromData(dto, offer.getRole());

        lobbyService.assignUserToLobby(newUser, offer.getLobbyId());

        offer.setOfferStatus(JobOfferStatus.ACCEPTED);
        offer.setUpdatedAt(System.currentTimeMillis());
        offer = jobOfferRepo.save(offer);

        return jobOfferMapper.toSummaryDTO(offer);
    }
}
