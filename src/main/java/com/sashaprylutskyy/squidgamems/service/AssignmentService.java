package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentResponsePlayersDTO;
import com.sashaprylutskyy.squidgamems.model.enums.Env;
import com.sashaprylutskyy.squidgamems.model.enums.Sex;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import com.sashaprylutskyy.squidgamems.model.mapper.UserMapper;
import com.sashaprylutskyy.squidgamems.repository.AssignmentRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepo;
    private final UserMapper userMapper;


    public AssignmentService(AssignmentRepository assignmentRepo, UserMapper userMapper) {
        this.assignmentRepo = assignmentRepo;
        this.userMapper = userMapper;
    }

    public Assignment getAssignment_Env_byEnvIdAndUserId(Env env, Long envId, Long userId) {
        return assignmentRepo.findByEnvAndEnvIdAndUserId(env, envId, userId);
    }

    public Assignment getAssignment_Lobby_byUser(User user) {
        return assignmentRepo.findAssignmentByEnvAndUser(Env.LOBBY, user)
                .orElseThrow(() -> new NoResultException
                        ("User %s is not assigned to any lobby.".formatted(user.getEmail())));
    }

    public List<Assignment> getAssignmentList(Env env, Long envId) {
        return assignmentRepo.findAssignments(env, envId);
    }

    public List<Assignment> getAssignmentList(Env env, Long envId, UserStatus status) {
        return assignmentRepo.findAssignments(env, envId, status);
    }

    public List<Assignment> getAssignmentList(Env env, Long envId, UserStatus status, Sex sex) {
        return assignmentRepo.findAssignments(env, envId, status, sex);
    }

    public void assignUserToLobby(User user, Long lobbyId) {
        Assignment assignment = new Assignment(
                Env.LOBBY,
                lobbyId,
                user,
                null,
                System.currentTimeMillis()
        );
        assignmentRepo.save(assignment);
    }

    @Transactional
    public AssignmentResponsePlayersDTO assignPlayersToCompetition(
            Long competitionId, List<User> players, User principal) {

        Long now = System.currentTimeMillis();

        List<Assignment> assignmentsToSave = new ArrayList<>();
        List<User> playerEntities = new ArrayList<>();

        for (User player : players) {
            Assignment assignment = getAssignment_Env_byEnvIdAndUserId(
                    Env.COMPETITION, competitionId, player.getId()
            );
            if (assignment != null) {
                continue;
            }

            playerEntities.add(player);
            Assignment newAssignment = new Assignment(
                    Env.COMPETITION,
                    competitionId,
                    player,
                    principal,
                    now
            );
            assignmentsToSave.add(newAssignment);
        }

        assignmentRepo.saveAll(assignmentsToSave);
        return toResponseDTO(principal, competitionId, now, playerEntities);
    }

    @Transactional
    public AssignmentResponsePlayersDTO removePlayersFromCompetition(
            Long competitionId, List<User> playerEntities, User principal) {

        Long now = System.currentTimeMillis();

        List<Assignment> assignmentsToDelete = new ArrayList<>();
        List<User> playersToDelete = new ArrayList<>();

        for (User player : playerEntities) {
            Assignment assignment = getAssignment_Env_byEnvIdAndUserId(
                    Env.COMPETITION, competitionId, player.getId()
            );
            if (assignment != null) {
                assignmentsToDelete.add(assignment);
                playersToDelete.add(assignment.getUser());
            }
        }
        assignmentRepo.deleteAll(assignmentsToDelete);

        return toResponseDTO(principal, competitionId, now, playersToDelete);
    }

    private AssignmentResponsePlayersDTO toResponseDTO(
            User principal, Long competitionId, Long currentTime, List<User> playerEntities) {

        AssignmentResponsePlayersDTO response = new AssignmentResponsePlayersDTO();
        response.setCompetitionId(competitionId);
        response.setProcessedAt(currentTime);
        response.setProcessedBy(userMapper.toSummaryDTO(principal));
        response.setPlayers(playerEntities.stream()
                .map(userMapper::toSummaryDTO)
                .toList());
        return response;
    }

}
