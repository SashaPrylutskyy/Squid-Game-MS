package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.Vote;
import com.sashaprylutskyy.squidgamems.model.dto.roundResult.RoundResultSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.vote.VoteResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.vote.VoteResultDTO;
import com.sashaprylutskyy.squidgamems.model.enums.Env;
import com.sashaprylutskyy.squidgamems.model.mapper.VoteMapper;
import com.sashaprylutskyy.squidgamems.repository.VoteRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final VoteRepo voteRepo;
    private final VoteMapper voteMapper;
    private final UserService userService;
    private final RoundService roundService;
    private final AssignmentService assignmentService;
    private final RoundResultService roundResultService;

    public VoteService(VoteRepo voteRepo, VoteMapper voteMapper, UserService userService, RoundService roundService, AssignmentService assignmentService, RoundResultService roundResultService) {
        this.voteRepo = voteRepo;
        this.voteMapper = voteMapper;
        this.userService = userService;
        this.roundService = roundService;
        this.assignmentService = assignmentService;
        this.roundResultService = roundResultService;
    }

    public Vote getVote(User player, Round round) {
        return voteRepo.findByPlayerAndRound(player, round);
    }

    public List<VoteResponseDTO> getVotes(Long roundId) {
        List<Vote> votes = voteRepo.findAllByRoundId(roundId);
        return voteMapper.toResponseDTOList(votes);
    }

    public List<VoteResponseDTO> getVotes(Long roundId, Boolean isQuit) {
        List<Vote> votes = voteRepo.findAllByRoundIdAndIsQuit(roundId, isQuit);
        return voteMapper.toResponseDTOList(votes);
    }

    @Transactional
    public VoteResponseDTO vote(Long roundId, boolean isQuit) {
        User principal = userService.getPrincipal();
        Round round = roundService.getById(roundId);

        if (getVote(principal, round) != null) {
            throw new RuntimeException("You've already voted.");
        }

        Vote vote = new Vote(principal, round, isQuit);
        vote = voteRepo.save(vote);
        return voteMapper.toResponseDTO(vote);
    }

    //todo подумати, чи отримання користувачів через Assignments не буде кращим
    public VoteResultDTO getResults(Long roundId) {
        RoundResultSummaryDTO reportedPlayers = roundResultService.getReportedPlayers(roundId, true);

        int continueGame = getVotes(roundId, true).size();
        int quitGame = getVotes(roundId, false).size();
        int remaining = reportedPlayers.getPlayers().size() - getVotes(roundId).size();

        return new VoteResultDTO(continueGame, quitGame, remaining);
    }
}
