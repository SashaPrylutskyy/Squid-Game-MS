package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.RoundResult;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.enums.Env;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import com.sashaprylutskyy.squidgamems.model.mapper.RoundResultMapper;
import com.sashaprylutskyy.squidgamems.repository.RoundResultRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoundResultService {

    private final RoundService roundService;
    private final UserService userService;
    private final RoundResultMapper roundResultMapper;
    private final RoundResultRepo roundResultRepo;
    private final AssignmentService assignmentService;

    public RoundResultService(RoundService roundService, UserService userService,
                              RoundResultMapper roundResultMapper, RoundResultRepo roundResultRepo, AssignmentService assignmentService) {
        this.roundService = roundService;
        this.userService = userService;
        this.roundResultMapper = roundResultMapper;
        this.roundResultRepo = roundResultRepo;
        this.assignmentService = assignmentService;
    }

    public List<RoundResult> getReports(Long roundId) {
        return roundResultRepo.findAllByRoundId(roundId);
    }

    public RoundResultSummaryDTO getReportsSummary(Long roundId) {
        List<RoundResult> rrs = roundResultRepo.findAllByRoundId(roundId);
        return roundResultMapper.toSummaryDTO(rrs);
    }

    public RoundResultSummaryDTO getReportsSummary(Long roundId, boolean isAlive) {
        List<RoundResult> rrs = (isAlive) ?
                roundResultRepo.findAllByRoundIdAndStatus(roundId, UserStatus.PASSED) :
                roundResultRepo.findAllByRoundIdAndStatus(roundId, UserStatus.ELIMINATED);

        return roundResultMapper.toSummaryDTO(rrs);
    }

    @Transactional
    public RoundResultResponseDTO reportPlayerResult(Long roundId, Long playerId,
                                                     UserStatus userStatus) {
        Round round = roundService.getById(roundId);
        User player = userService.getUserById(playerId);
        User principal = userService.getPrincipal();

        RoundResult rr = new RoundResult(
                round, player, userStatus,
                System.currentTimeMillis(),
                principal
        );
        rr = roundResultRepo.save(rr);
        return roundResultMapper.toResponseDTO(rr);
    }

    @Transactional
    public RoundResultSummaryDTO confirmPlayerStatus(RoundResultRequestDTO dto) {
        User principal = userService.getPrincipal();
        Long now = System.currentTimeMillis();

        List<RoundResult> rrs = roundResultRepo.findAllBy(dto.getRoundId(), dto.getPlayerIds());
        for (RoundResult rr : rrs) {
            rr.setConfirmedBy(principal);
            rr.setConfirmedAt(now);

            if (dto.isValid()) {
                UserStatus userStatus = (rr.getStatus() == UserStatus.ELIMINATED)
                        ? UserStatus.PASSED : UserStatus.ELIMINATED;

                rr.setStatus(userStatus);
                if (userStatus == UserStatus.ELIMINATED) {
                    User user = rr.getUser();
                    user.setStatus(userStatus); //todo переконатися, що статус гравця зберігається до БД
                }
            }
        }
        roundResultRepo.saveAll(rrs);

        return roundResultMapper.toSummaryDTO(rrs);
    }

    @Transactional
    public void setPlayersStatusTimeout(Long competitionId, Round round) {
        Long now = System.currentTimeMillis();

        List<RoundResult> rrs = getReports(round.getId());
        List<Long> existingUserIds = rrs.stream()
                .map(rr -> rr.getUser().getId())
                .toList();

        List<Assignment> assignments = assignmentService.getAssignmentsListExcludingUsersByIds(
                Env.COMPETITION, competitionId, existingUserIds, UserStatus.ALIVE);
        List<User> playersToEliminate = assignments.stream()
                .map(Assignment::getUser)
                .toList();

        List<RoundResult> rrsToSave = new ArrayList<>(playersToEliminate.size());
        for (User player : playersToEliminate) {
            RoundResult rr = new RoundResult(round, player, UserStatus.ELIMINATED, now, null); //вожливо все ж призначати статус TIMEOUT.
            rrsToSave.add(rr);
            rr.getUser().setStatus(UserStatus.ELIMINATED);
        }
        roundResultRepo.saveAll(rrsToSave);
    }
}
