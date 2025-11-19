package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.*;
import com.sashaprylutskyy.squidgamems.model.dto.user.PlayerStatusResponseDTO;
import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;
import com.sashaprylutskyy.squidgamems.model.enums.Env;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import com.sashaprylutskyy.squidgamems.repository.RoundRepo;
import com.sashaprylutskyy.squidgamems.repository.RoundResultRepo;
import com.sashaprylutskyy.squidgamems.repository.VoteRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AssignmentService assignmentService;
    @Mock
    private CompetitionService competitionService;
    @Mock
    private RoundRepo roundRepo;
    @Mock
    private VoteRepo voteRepo;
    @Mock
    private RoundResultRepo roundResultRepo;

    @Spy
    @InjectMocks
    private UserService userService;

    private User player;
    private Competition competition;
    private Round round;

    @BeforeEach
    void setUp() {
        player = new User();
        player.setId(1L);
        player.setStatus(UserStatus.ALIVE);

        competition = new Competition();
        competition.setId(10L);
        competition.setTitle("Test Competition");
        competition.setStatus(CompetitionRoundStatus.ACTIVE);

        round = new Round();
        round.setId(100L);
        round.setStatus(CompetitionRoundStatus.VOTING);
        Game game = new Game();
        game.setGameTitle("Test Game");
        round.setGame(game);

        // Mock getPrincipal to return our player
        doReturn(player).when(userService).getPrincipal();
    }

    @Test
    void getPlayerStatus_NotInGame() {
        when(assignmentService.getAssignment_Env_ByType(Env.COMPETITION, player))
                .thenReturn(Optional.empty());

        PlayerStatusResponseDTO result = userService.getPlayerStatus();

        assertNull(result.getStatusInCompetition());
        assertNull(result.getCompetition());
    }

    @Test
    void getPlayerStatus_CompetitionCompleted() {
        Assignment assignment = new Assignment();
        assignment.setEnvId(competition.getId());
        when(assignmentService.getAssignment_Env_ByType(Env.COMPETITION, player))
                .thenReturn(Optional.of(assignment));

        competition.setStatus(CompetitionRoundStatus.COMPLETED);
        when(competitionService.getById(competition.getId())).thenReturn(competition);

        PlayerStatusResponseDTO result = userService.getPlayerStatus();

        assertNull(result.getStatusInCompetition());
    }

    @Test
    void getPlayerStatus_ActiveRound_CanVote() {
        Assignment assignment = new Assignment();
        assignment.setEnvId(competition.getId());
        when(assignmentService.getAssignment_Env_ByType(Env.COMPETITION, player))
                .thenReturn(Optional.of(assignment));

        competition.setCurrentRoundId(round.getId());
        when(competitionService.getById(competition.getId())).thenReturn(competition);
        when(roundRepo.findById(round.getId())).thenReturn(Optional.of(round));

        when(voteRepo.existsByPlayerAndRound(player, round)).thenReturn(false);

        RoundResult rr = new RoundResult();
        rr.setStatus(UserStatus.PASSED);
        when(roundResultRepo.findTopByUserOrderByIdDesc(player)).thenReturn(Optional.of(rr));

        PlayerStatusResponseDTO result = userService.getPlayerStatus();

        assertNotNull(result.getCompetition());
        assertEquals(competition.getId(), result.getCompetition().getId());
        assertEquals(UserStatus.PASSED, result.getStatusInCompetition());
        assertNotNull(result.getCurrentRound());
        assertEquals(round.getId(), result.getCurrentRound().getId());
        assertTrue(result.getActiveVote().isCanVote());
        assertFalse(result.getActiveVote().isHasVoted());
    }

    @Test
    void getPlayerStatus_ActiveRound_CannotVote_AlreadyVoted() {
        Assignment assignment = new Assignment();
        assignment.setEnvId(competition.getId());
        when(assignmentService.getAssignment_Env_ByType(Env.COMPETITION, player))
                .thenReturn(Optional.of(assignment));

        competition.setCurrentRoundId(round.getId());
        when(competitionService.getById(competition.getId())).thenReturn(competition);
        when(roundRepo.findById(round.getId())).thenReturn(Optional.of(round));

        when(voteRepo.existsByPlayerAndRound(player, round)).thenReturn(true);

        RoundResult rr = new RoundResult();
        rr.setStatus(UserStatus.PASSED);
        when(roundResultRepo.findTopByUserOrderByIdDesc(player)).thenReturn(Optional.of(rr));

        PlayerStatusResponseDTO result = userService.getPlayerStatus();

        assertFalse(result.getActiveVote().isCanVote());
        assertTrue(result.getActiveVote().isHasVoted());
    }

    @Test
    void getPlayerStatus_ActiveRound_CannotVote_Dead() {
        Assignment assignment = new Assignment();
        assignment.setEnvId(competition.getId());
        when(assignmentService.getAssignment_Env_ByType(Env.COMPETITION, player))
                .thenReturn(Optional.of(assignment));

        competition.setCurrentRoundId(round.getId());
        when(competitionService.getById(competition.getId())).thenReturn(competition);
        when(roundRepo.findById(round.getId())).thenReturn(Optional.of(round));

        when(voteRepo.existsByPlayerAndRound(player, round)).thenReturn(false);

        RoundResult rr = new RoundResult();
        rr.setStatus(UserStatus.ELIMINATED);
        when(roundResultRepo.findTopByUserOrderByIdDesc(player)).thenReturn(Optional.of(rr));

        PlayerStatusResponseDTO result = userService.getPlayerStatus();

        assertFalse(result.getActiveVote().isCanVote());
        assertEquals(UserStatus.ELIMINATED, result.getStatusInCompetition());
    }

    @Test
    void getPlayerStatus_ActiveRound_CannotVote_NotParticipated() {
        Assignment assignment = new Assignment();
        assignment.setEnvId(competition.getId());
        when(assignmentService.getAssignment_Env_ByType(Env.COMPETITION, player))
                .thenReturn(Optional.of(assignment));

        competition.setCurrentRoundId(round.getId());
        when(competitionService.getById(competition.getId())).thenReturn(competition);
        when(roundRepo.findById(round.getId())).thenReturn(Optional.of(round));

        when(voteRepo.existsByPlayerAndRound(player, round)).thenReturn(false);
        when(roundResultRepo.findTopByUserOrderByIdDesc(player)).thenReturn(Optional.empty());

        PlayerStatusResponseDTO result = userService.getPlayerStatus();

        assertFalse(result.getActiveVote().isCanVote());
        assertEquals(UserStatus.ALIVE, result.getStatusInCompetition());
    }
}
