package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.JobOffer;
import com.sashaprylutskyy.squidgamems.model.enums.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.JobOfferRequestUserDTO;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserRequestDTO;
import com.sashaprylutskyy.squidgamems.model.enums.JobOfferStatus;
import com.sashaprylutskyy.squidgamems.model.mapper.JobOfferMapper;
import com.sashaprylutskyy.squidgamems.model.mapper.UserMapper;
import com.sashaprylutskyy.squidgamems.repository.JobOfferRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobOfferService {

    private final JobOfferRepository jobOfferRepo;
    private final UserService userService;
    private final JobOfferMapper jobOfferMapper;
    private final RefCodeService refCodeService;
    private final UserMapper userMapper;
    private final AssignmentService assignmentService;

    public JobOfferService(JobOfferRepository jobOfferRepo, UserService userService,
                           JobOfferMapper jobOfferMapper,
                           RefCodeService refCodeService, UserMapper userMapper, AssignmentService assignmentService) {
        this.jobOfferRepo = jobOfferRepo;
        this.userService = userService;
        this.jobOfferMapper = jobOfferMapper;
        this.refCodeService = refCodeService;
        this.userMapper = userMapper;
        this.assignmentService = assignmentService;
    }

    public JobOffer getOfferByToken(UUID token) {
        return jobOfferRepo.findByToken(token)
                .orElseThrow(() -> new NoResultException("Job offer %s isn't found".formatted(token)));
    }

    @Transactional
    public JobOfferResponseDTO makeJobOffer(JobOfferRequestDTO dto) {
        User principal = userService.getPrincipal();
        Role role = dto.getRole();
        Assignment currentLobby = assignmentService.getAssignment_Lobby_byUser(principal);

        try {
            User user = userService.getUserByEmail(dto.getEmail());
            if (user != null) {
                throw new DuplicateKeyException("User %s is already registered".formatted(dto.getEmail()));
            }
        } catch (UsernameNotFoundException ignored) {}

        if (role == Role.PLAYER || role == Role.HOST || role == Role.VIP) {
            throw new RuntimeException("Unable to make a job offer neither for VIP nor HOST nor PLAYER");
        }

        JobOffer jobOffer = new JobOffer(
                currentLobby.getEnvId(),
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
    public JobOfferSummaryDTO acceptJobOffer(UUID token, JobOfferRequestUserDTO dto) {
        JobOffer offer = getOfferByToken(token);

        if (offer.getOfferStatus() != JobOfferStatus.AWAITING) {
            throw new IllegalStateException("Job offer status is" + offer.getOfferStatus().name());
        }
        UserRequestDTO userDTO = userMapper.toUserRequestDTO(dto);
        userDTO.setEmail(offer.getEmail());
        userDTO.setRole(offer.getRole());

        User employee = userService.createUserFromData(userDTO);

        assignmentService.assignUserToLobby(employee, offer.getLobbyId());

        offer.setOfferStatus(JobOfferStatus.ACCEPTED);
        offer.setUpdatedAt(System.currentTimeMillis());
        offer = jobOfferRepo.save(offer);

        if (employee.getRole() == Role.SALESMAN) {
            refCodeService.assignRefCode(employee);
        }

        return jobOfferMapper.toSummaryDTO(offer);
    }
}
