package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.*;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentRequestPlayersDTO;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentResponsePlayersDTO;
import com.sashaprylutskyy.squidgamems.model.dto.refCode.RefCodeSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.round.CurrentRoundDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.*;
import com.sashaprylutskyy.squidgamems.model.enums.*;
import com.sashaprylutskyy.squidgamems.model.mapper.RefCodeMapper;
import com.sashaprylutskyy.squidgamems.model.mapper.UserMapper;
import com.sashaprylutskyy.squidgamems.repository.RoundRepo;
import com.sashaprylutskyy.squidgamems.repository.UserRepository;
import com.sashaprylutskyy.squidgamems.repository.VoteRepo;
import com.sashaprylutskyy.squidgamems.security.JwtService;
import jakarta.persistence.NoResultException;
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
    private final RoundRepo roundRepo;
    private final VoteRepo voteRepo;


    public UserService(UserRepository userRepo, JwtService jwtService,
                       PasswordEncoder encoder, UserMapper userMapper,
                       RefCodeService refCodeService, RefCodeMapper refCodeMapper,
                       AssignmentService assignmentService, CompetitionService competitionService, RoundRepo roundRepo, VoteRepo voteRepo) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.encoder = encoder;
        this.userMapper = userMapper;
        this.refCodeService = refCodeService;
        this.refCodeMapper = refCodeMapper;
        this.assignmentService = assignmentService;
        this.competitionService = competitionService;
        this.roundRepo = roundRepo;
        this.voteRepo = voteRepo;
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

    public List<UserSummaryDTO> getListOfUsers(Env env, Long envId) {
        List<Assignment> assignments = assignmentService.getListOfAssignments(env, envId);
        return userMapper.toSummaryDTOList(assignments);
    }

    public List<UserSummaryDTO> getListOfUsers(Env env, Long envId, UserStatus userStatus) {
        List<Assignment> assignments = assignmentService.getListOfAssignments(env, envId, userStatus);
        return userMapper.toSummaryDTOList(assignments);
    }

    public List<UserSummaryDTO> getListOfUsers(Env env, Long envId, UserStatus userStatus, Sex sex) {
        List<Assignment> assignments = assignmentService.getListOfAssignments(env, envId, userStatus, sex);
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
    public AssignmentResponsePlayersDTO assignPlayers_Competition(boolean assign, AssignmentRequestPlayersDTO dto) {
        User principal = getPrincipal();
        Long lobbyId = assignmentService.getAssignment_Lobby(principal).getEnvId();
        Competition competition = competitionService.getById(dto.getCompetitionId());
        Long competitionId = competition.getId();

        List<Long> playerIds = dto.getPlayerIds();
        List<User> playerEntities = new ArrayList<>();

        for (Long playerId : playerIds) {
            try {
                Assignment assignment = assignmentService
                        .getAssignment_Env(Env.LOBBY, lobbyId, playerId);
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

    public List<UserSummaryDTO> getUsersByRole(Role role, Boolean isAssigned) {
        User principal = getPrincipal();

        Assignment lobbyAssignment = assignmentService.getAssignment_Lobby(principal);
        Long lobbyId = lobbyAssignment.getEnvId();

        List<User> users;

        if (isAssigned == null) {
            users = userRepo.findAllPlayersInLobby(lobbyId, role, UserStatus.ALIVE);
        } else if (!isAssigned) {
            users = userRepo.findAvailablePlayersInLobby(lobbyId, role, UserStatus.ALIVE);
        } else {
            users = userRepo.findAssignedPlayersInLobby(lobbyId, role, UserStatus.ALIVE);
        }
        return userMapper.mapUsersToSummaryDTOs(users);
    }

    public WorkerAssignmentResponseDTO getWorkerAssignment() {
        User worker = getPrincipal();

        Assignment lobby = assignmentService.getAssignment_Lobby(worker);
        Competition currentCompetition = competitionService.getActiveByLobbyId(lobby.getEnvId());

        if (currentCompetition.getCurrentRoundId() == null) {
            throw new RuntimeException("There is no active round yet.");
        }

        Round currentRound = roundRepo.findById(currentCompetition.getCurrentRoundId())
                .orElseThrow(() -> new NoResultException(
                        "Round No.%d is not found.".formatted(currentCompetition.getCurrentRoundId()))
                );

        CurrentRoundDTO roundDTO = new CurrentRoundDTO(
                currentRound.getId(),
                currentCompetition.getId(),
                currentRound.getGame().getGameTitle()
        );

        List<PlayerReportDTO> playersToReport = userRepo.findPlayersWithRoundStatus(
                currentCompetition.getId(),
                currentRound.getId()
        );

        return new WorkerAssignmentResponseDTO(roundDTO, playersToReport);
    }

    //todo AI написав якусь бургу, яку варто дуууже пристально перевірити, бо відразу видно хуйню
    public PlayerStatusResponseDTO getPlayerStatus() {
        User player = getPrincipal();

        // 1. Отримуємо призначення (як ми робили раніше)
        Assignment assignment = assignmentService
                .getAssignment_Env_ByType(Env.COMPETITION, player)
                .orElse(null);

        if (assignment == null) {
            return PlayerStatusResponseDTO.notInGame();
        }

        Competition competition = competitionService.getById(assignment.getEnvId());

        // Перевірка, чи змагання активне
        if (competition.getStatus() == CompetitionRoundStatus.COMPLETED ||
                competition.getStatus() == CompetitionRoundStatus.CANCELED ||
                competition.getStatus() == CompetitionRoundStatus.ARCHIVED) {
            return PlayerStatusResponseDTO.notInGame();
        }

        PlayerStatusResponseDTO response = new PlayerStatusResponseDTO();

        // Заповнюємо Competition DTO
        response.setCompetition(new PlayerCompetitionDTO(
                competition.getId(),
                competition.getTitle()
        ));

        // Заповнюємо статус гравця
        response.setStatusInCompetition(player.getStatus());

        // 2. Логіка Раунду та Голосування
        if (competition.getCurrentRoundId() != null) {
            Round currentRound = roundRepo.findById(competition.getCurrentRoundId()).orElse(null);

            if (currentRound != null) {
                // Заповнюємо Round DTO (статус буде ACTIVE або VOTING)
                response.setCurrentRound(new PlayerRoundDTO(
                        currentRound.getId(),
                        currentRound.getGame().getGameTitle(),
                        currentRound.getStatus() // <-- Тут буде VOTING, коли прийде час
                ));

                // ✨ ЛОГІКА ГОЛОСУВАННЯ:

                // 1. Чи зараз йде фаза голосування?
                boolean isVotingPhase = currentRound.getStatus() == CompetitionRoundStatus.VOTING;

                // 2. Чи гравець вже проголосував? (Використовуємо VoteRepo)
                boolean hasVoted = voteRepo.existsByPlayerAndRound(player, currentRound);

                // 3. Чи може голосувати? (Тільки якщо йде фаза голосування і ще не голосував)
                // Додатково: можна перевірити, чи гравець ALIVE (мертві не голосують)
                boolean canVote = isVotingPhase && !hasVoted && (player.getStatus() == UserStatus.ALIVE);

                response.setActiveVote(new PlayerVoteDTO(
                        canVote,
                        hasVoted
                ));
            }
        }

        return response;
    }
}
