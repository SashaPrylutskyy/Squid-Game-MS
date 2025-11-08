package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.RoundResult;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import com.sashaprylutskyy.squidgamems.model.mapper.RoundResultMapper;
import com.sashaprylutskyy.squidgamems.repository.RoundResultRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundResultService {

    private final RoundService roundService;
    private final UserService userService;
    private final RoundResultMapper roundResultMapper;
    private final RoundResultRepo roundResultRepo;

    public RoundResultService(RoundService roundService, UserService userService,
                              RoundResultMapper roundResultMapper, RoundResultRepo roundResultRepo) {
        this.roundService = roundService;
        this.userService = userService;
        this.roundResultMapper = roundResultMapper;
        this.roundResultRepo = roundResultRepo;
    }

    public RoundResultSummaryDTO getReportedPlayers(Long roundId) {
        List<RoundResult> rrs = roundResultRepo.findAllByRoundId(roundId);
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
            }
        }
        roundResultRepo.saveAll(rrs);

        return roundResultMapper.toSummaryDTO(rrs);

    }
}
