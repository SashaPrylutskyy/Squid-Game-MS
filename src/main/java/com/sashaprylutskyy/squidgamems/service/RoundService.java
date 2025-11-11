package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Competition;
import com.sashaprylutskyy.squidgamems.model.Game;
import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundListResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.vote.VoteResultDTO;
import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;
import com.sashaprylutskyy.squidgamems.model.mapper.RoundMapper;
import com.sashaprylutskyy.squidgamems.repository.CompetitionRepo;
import com.sashaprylutskyy.squidgamems.repository.RoundRepo;
import com.sashaprylutskyy.squidgamems.util.TimerService;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundService {

    private final RoundMapper roundMapper;
    private final RoundRepo roundRepo;
    private final GameService gameService;
    private final CompetitionService competitionService;
    private final TimerService timerService;
    private final CompetitionRepo competitionRepo;
    private final RoundResultService roundResultService;
    private final VoteService voteService;

    public RoundService(RoundMapper roundMapper, RoundRepo roundRepo, GameService gameService,
                        CompetitionService competitionService, TimerService timerService,
                        CompetitionRepo competitionRepo, RoundResultService roundResultService,
                        VoteService voteService) {
        this.roundMapper = roundMapper;
        this.roundRepo = roundRepo;
        this.gameService = gameService;
        this.competitionService = competitionService;
        this.timerService = timerService;
        this.competitionRepo = competitionRepo;
        this.roundResultService = roundResultService;
        this.voteService = voteService;
    }

    public Round getById(Long roundId) {
        return roundRepo.findById(roundId)
                .orElseThrow(() -> new NoResultException(
                        "Round No.%d is not found.".formatted(roundId))
                );
    }

    private Round getCurrentRound(Long competitionId) {
        Competition competition = competitionService.getById(competitionId);
        return roundRepo.findByIdAndCompetitionId(competition.getCurrentRoundId(), competitionId)
                .orElseThrow(() -> new NoResultException("Nothing was found."));
    }

    public RoundResponseDTO getCurrentRoundInfo(Long competitionId) {
        Round round = getCurrentRound(competitionId);
        return roundMapper.toResponseDTO(round);
    }

    public Round getNextRound(Long competitionId) {
        return roundRepo.findNextRounds(competitionId).stream()
                .findFirst()
                .orElseThrow(() -> new NoResultException("There are no more rounds left."));
    }

    @Transactional
    public RoundListResponseDTO addRounds(RoundRequestDTO dto) {
        Competition currentCompetition = competitionService.getById(dto.getCompetitionId());
        if (currentCompetition.getStatus() != CompetitionRoundStatus.PENDING) {
            throw new RuntimeException("Unable to add a new round to a competition, cuz it's ACTIVE/COMPLETED/CANCELED or ARCHIVED.");
        }
        List<Game> games = gameService.getGamesByIds(dto.getGameIds());
        List<Round> rounds = roundMapper.toEntityList(dto, games);
        roundRepo.saveAll(rounds);

        return roundMapper.toListResponseDTO(dto.getCompetitionId(), rounds);
    }

    @Transactional
    public RoundResponseDTO startNextRound(Long competitionId) {
        Competition currentCompetition = competitionService.getById(competitionId);
        Round currentRound = getById(currentCompetition.getCurrentRoundId());
        if (currentRound.getStatus() == CompetitionRoundStatus.ACTIVE) {
            throw new RuntimeException("Cannot start the next round while the previous one is still running");
        }

        Long lastRoundId = currentCompetition.getCurrentRoundId();
        if (lastRoundId != null) {
            VoteResultDTO voteResults = voteService.getResults(lastRoundId);

            if (voteResults.getRemaining() > 0) {
                throw new RuntimeException("Cannot start next round: "
                    + voteResults.getRemaining() + " players have not voted for the previous round yet.");
            }
        }

        Round round = getNextRound(competitionId);
        round.setStatus(CompetitionRoundStatus.ACTIVE);
        round.setStartedAt(System.currentTimeMillis());

        currentCompetition.setCurrentRoundId(round.getId());
        currentCompetition.setStatus(CompetitionRoundStatus.ACTIVE);

//        timerService.runAfterDelay(() -> endRound(round, currentCompetition),
//                60 * 1000 * round.getGame().getGameDuration());
        timerService.runAfterDelay(() -> endRound(round, currentCompetition), //todo: REVERT BACK! This is just for testing purposes.
                60 * 1000);
        return roundMapper.toResponseDTO(round);
    }

    @Transactional
    protected Round endRound(Round round, Competition currentCompetition) {
        round.setEndedAt(System.currentTimeMillis());
        round.setStatus(CompetitionRoundStatus.COMPLETED);

        roundResultService.setPlayersStatusTimeout(currentCompetition.getId(), round);

        try {
            getNextRound(round.getCompetitionId());
        } catch (NoResultException e) {
            currentCompetition.setStatus(CompetitionRoundStatus.COMPLETED);
            competitionRepo.save(currentCompetition);
        }
        return roundRepo.save(round);
    }

    //todo cancel a runAfterDelay() method using a cancel() method
    @Transactional
    public RoundResponseDTO endCurrentRound(Long competitionId) {
        Competition currentCompetition = competitionService.getById(competitionId);
        Round round = getCurrentRound(competitionId);
        round = endRound(round, currentCompetition);

        roundResultService.setPlayersStatusTimeout(currentCompetition.getId(), round);

        return roundMapper.toResponseDTO(round);
    }
}
