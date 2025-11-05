package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.Competition;
import com.sashaprylutskyy.squidgamems.model.RefCode;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentRequestPlayersDTO;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentResponsePlayersDTO;
import com.sashaprylutskyy.squidgamems.model.dto.refCode.RefCodeSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.*;
import com.sashaprylutskyy.squidgamems.model.enums.Env;
import com.sashaprylutskyy.squidgamems.model.enums.Sex;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import com.sashaprylutskyy.squidgamems.model.mapper.RefCodeMapper;
import com.sashaprylutskyy.squidgamems.model.mapper.UserMapper;
import com.sashaprylutskyy.squidgamems.repository.UserRepository;
import com.sashaprylutskyy.squidgamems.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final RefCodeService refCodeService;
    private final RefCodeMapper refCodeMapper;
    private final AssignmentService assignmentService;
    private final CompetitionService competitionService;


    public UserService(UserRepository userRepo, JwtService jwtService,
                       PasswordEncoder encoder, UserMapper userMapper,
                       RefCodeService refCodeService, RefCodeMapper refCodeMapper,
                       AssignmentService assignmentService, CompetitionService competitionService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.encoder = encoder;
        this.userMapper = userMapper;
        this.refCodeService = refCodeService;
        this.refCodeMapper = refCodeMapper;
        this.assignmentService = assignmentService;
        this.competitionService = competitionService;
    }

    public User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new NullPointerException("User is not authenticated.");
        }

        return (User) authentication.getPrincipal();
    }

    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(email)));
    }

    public User getUserById(Long id) {
        return userRepo.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User No.%d is not found".formatted(id)));
    }

    public List<UserSummaryDTO> getUserList(Env env, Long envId) {
        List<Assignment> assignments = assignmentService
                .getAssignmentList(env, envId);

        return userMapper.toSummaryDTOList(assignments);
    }

    public List<UserSummaryDTO> getUserList(Env env, Long envId,
                                            UserStatus userStatus) {
        List<Assignment> assignments = assignmentService
                .getAssignmentList(env, envId, userStatus);

        return userMapper.toSummaryDTOList(assignments);
    }

    public List<UserSummaryDTO> getUserList(Env env, Long envId,
                                            UserStatus userStatus,
                                            Sex sex) {
        List<Assignment> assignments = assignmentService
                .getAssignmentList(env, envId, userStatus, sex);

        return userMapper.toSummaryDTOList(assignments);
    }

    @Transactional
    public User createUserFromData(UserRequestDTO dto) {
        try {
            Long currentTime = System.currentTimeMillis();

            User user = userMapper.toEntity(dto);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setStatus(UserStatus.ALIVE);
            user.setCreatedAt(currentTime);
            user.setUpdatedAt(currentTime);

            return userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("Email is already taken.");
        }
    }

    public RefCodeSummaryDTO getMyRefCode() {
        User principal = getPrincipal();
        RefCode refCode = refCodeService.getRefCode(principal);
        return refCodeMapper.toSummaryDTO(refCode);
    }

    public String login(LoginRequestDTO dto) {
        User user = getUserByEmail(dto.getEmail());
        if (encoder.matches(dto.getPassword(), user.getPassword())) {
            return jwtService.generateToken(user);
        }
        throw new RuntimeException("Invalid credentials");
    }

    @Transactional
    public AssignmentResponsePlayersDTO assignPlayersToFromCompetition(boolean assign, AssignmentRequestPlayersDTO dto) {
        User principal = getPrincipal();
        Long lobbyId = assignmentService.getAssignment_Lobby_byUser(principal).getEnvId();
        Competition competition = competitionService.getById(dto.getCompetitionId());
        Long competitionId = competition.getId();

        List<Long> playerIds = dto.getPlayerIds();
        List<User> playerEntities = new ArrayList<>();

        for (Long playerId : playerIds) {
            try {
                Assignment assignment = assignmentService
                        .getAssignment_Env_byEnvIdAndUserId(Env.LOBBY, lobbyId, playerId);
                if (assignment != null) {
                    playerEntities.add(assignment.getUser());
                }
            } catch (UsernameNotFoundException ignored) {
            }
        }

        if (assign) {
            return assignmentService.assignPlayersToCompetition(competitionId, playerEntities, principal);
        } else {
            return assignmentService.removePlayersFromCompetition(competitionId, playerEntities, principal);
        }
    }

}
