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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundService {

    @Value("${game.voting-duration}")
    private Long votingDuration;

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

        return roundRepo.findByIdAndCompetition_Id(competition.getCurrentRoundId(), competitionId)
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
        Competition competition = competitionService.getById(dto.getCompetitionId());
        if (competition.getStatus() == CompetitionRoundStatus.PENDING ||
            competition.getStatus() == CompetitionRoundStatus.FUNDED) {

            List<Game> games = gameService.getGamesByIds(dto.getGameIds());
            List<Round> rounds = roundMapper.toEntityList(competition, games);
            roundRepo.saveAll(rounds);

            return roundMapper.toListResponseDTO(dto.getCompetitionId(), rounds);
        } else {
            throw new RuntimeException("Failed to add rounds: competition isn't in a PENDING or FUNDED status");
        }
    }

    @Transactional
    public RoundResponseDTO startNextRound(Long competitionId) {
        Competition competition = competitionService.getById(competitionId);
        if (competition.getStatus() != CompetitionRoundStatus.ACTIVE) {
            throw new RuntimeException("Failed to start a next round: competition isn't activated (ACTIVE).");
        }

        Long currentRoundId = competition.getCurrentRoundId();
        if (currentRoundId != null) {
            Round currentRound = getById(currentRoundId);
            if (currentRound.getStatus() == CompetitionRoundStatus.ACTIVE) {
                throw new RuntimeException("Cannot start the next round while the previous one is still running");
            }

            VoteResultDTO voteResults = voteService.getResults(currentRoundId);
            if (voteResults.getRemaining() > 0) {
                throw new RuntimeException("Cannot start next round: "
                        + voteResults.getRemaining() + " players have not voted for the previous round yet.");
            }

            currentRound.setStatus(CompetitionRoundStatus.COMPLETED);
        }

        Round nextRound = getNextRound(competitionId);
        nextRound.setStatus(CompetitionRoundStatus.ACTIVE);
        nextRound.setStartedAt(System.currentTimeMillis());

        competition.setCurrentRoundId(nextRound.getId());

//        timerService.runAfterDelay(() -> endRound(round, competition),
//                60 * 1000 * round.getGame().getGameDuration());
        timerService.runAfterDelay(() -> endRound(nextRound, competition), //This is just for testing purposes.
                60 * 1000);
        return roundMapper.toResponseDTO(nextRound);
    }

    @Transactional
    protected Round endRound(Round round, Competition currentCompetition) {
        round.setEndedAt(System.currentTimeMillis());
        round.setStatus(CompetitionRoundStatus.VOTING);

        roundResultService.setPlayersStatusTimeout(currentCompetition.getId(), round);

        return roundRepo.save(round);
    }

    //todo cancel a runAfterDelay() method using a cancel() method
    @Transactional
    public RoundResponseDTO endRound(Long competitionId) {
        Competition currentCompetition = competitionService.getById(competitionId);
        if (currentCompetition.getStatus() != CompetitionRoundStatus.ACTIVE) {
            throw new RuntimeException("Oops! Competition is ended.");
        }

        Round round = getCurrentRound(competitionId);
        if (round.getStatus() != CompetitionRoundStatus.ACTIVE) {
            throw new RuntimeException("Oops! Round is ended already.");
        }
        round = endRound(round, currentCompetition);

        return roundMapper.toResponseDTO(round);
    }

    public List<RoundResponseDTO> getRounds(Long competitionId) {
        List<Round> rounds = roundRepo.findAllByCompetitionId(competitionId);
        return roundMapper.toResponseDTOList(rounds);
    }
}