package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.JobOffer;
import com.sashaprylutskyy.squidgamems.model.Lobby;
import com.sashaprylutskyy.squidgamems.model.Role;
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
    private final RoleService roleService;
    private final JobOfferMapper jobOfferMapper;
    private final LobbyService lobbyService;
    private final RefCodeService refCodeService;
    private final UserMapper userMapper;

    public JobOfferService(JobOfferRepository jobOfferRepo, UserService userService,
                           RoleService roleService, JobOfferMapper jobOfferMapper,
                           LobbyService lobbyService, RefCodeService refCodeService,
                           UserMapper userMapper) {
        this.jobOfferRepo = jobOfferRepo;
        this.userService = userService;
        this.roleService = roleService;
        this.jobOfferMapper = jobOfferMapper;
        this.lobbyService = lobbyService;
        this.refCodeService = refCodeService;
        this.userMapper = userMapper;
    }

    public JobOffer getOfferByToken(UUID token) {
        return jobOfferRepo.findByToken(token)
                .orElseThrow(() -> new NoResultException("Job offer %s isn't found".formatted(token)));
    }

    public JobOfferResponseDTO makeJobOffer(JobOfferRequestDTO dto) {
        User principal = userService.getPrincipal();
        Role role = roleService.getRoleById(dto.getRoleId());
        Lobby currentLobby = lobbyService.getLobbyByUserId(principal.getId());

        try {
            User user = userService.getUserByEmail(dto.getEmail());
            if (user != null) {
                throw new DuplicateKeyException("User %s is already registered".formatted(dto.getEmail()));
            }
        } catch (UsernameNotFoundException ignored) {}

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
    public JobOfferSummaryDTO acceptJobOffer(UUID token, JobOfferRequestUserDTO dto) {
        JobOffer offer = getOfferByToken(token);

        if (offer.getOfferStatus() != JobOfferStatus.AWAITING) {
            throw new IllegalStateException("Job offer status is" + offer.getOfferStatus().name());
        }
        UserRequestDTO userDTO = userMapper.toUserRequestDTO(dto);
        userDTO.setEmail(offer.getEmail());
        userDTO.setRoleId(offer.getRole().getId());

        User employee = userService.createUserFromData(userDTO);

        lobbyService.assignUserToLobby(employee, offer.getLobbyId());

        offer.setOfferStatus(JobOfferStatus.ACCEPTED);
        offer.setUpdatedAt(System.currentTimeMillis());
        offer = jobOfferRepo.save(offer);

        if (employee.getRole().toString().equals("SALESMAN")) {
            refCodeService.assignRefCode(employee);
        }

        return jobOfferMapper.toSummaryDTO(offer);
    }
}
