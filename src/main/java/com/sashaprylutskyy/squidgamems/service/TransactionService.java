package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Competition;
import com.sashaprylutskyy.squidgamems.model.Transaction;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.transaction.TransactionDepositRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.transaction.TransactionResponseDTO;
import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;
import com.sashaprylutskyy.squidgamems.model.enums.TransactionType;
import com.sashaprylutskyy.squidgamems.model.mapper.TransactionMapper;
import com.sashaprylutskyy.squidgamems.repository.TransactionRepo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    @Value("${competition.min-vip-contributions}")
    private int minVipContributions;

    private final TransactionRepo transactionRepo;
    private final TransactionMapper transactionMapper;
    private final UserService userService;
    private final CompetitionService competitionService;

    public TransactionService(TransactionRepo transactionRepo,
                              TransactionMapper transactionMapper,
                              UserService userService,
                              CompetitionService competitionService) {
        this.transactionRepo = transactionRepo;
        this.transactionMapper = transactionMapper;
        this.userService = userService;
        this.competitionService = competitionService;
    }

    //todo
    @Transactional
    public Transaction createTransaction() {
        return null;
    }

    @Transactional
    public TransactionResponseDTO deposit(TransactionDepositRequestDTO dto) {
        User sender = userService.getPrincipal();

        Competition competition = competitionService.getById(dto.getCompetitionId());
        Long now = System.currentTimeMillis();

        Transaction transaction = new Transaction(
                competition, sender, null, dto.getAmount(), TransactionType.DEPOSIT, now);
        transaction = transactionRepo.save(transaction);

        checkCompetitionFundedStatus(competition);

        return transactionMapper.toResponseDTO(transaction);
    }

    @Transactional
    protected void checkCompetitionFundedStatus(Competition competition) {
        if (competition.getStatus() != CompetitionRoundStatus.PENDING) {
            return;
        }

        long currentContributions = transactionRepo.getTotalAmountByCompetitionIdAndType(
                competition.getId(), TransactionType.DEPOSIT);

        if (currentContributions >= minVipContributions) {
            competitionService.updateCompetitionStatus(
                    competition.getId(),
                    CompetitionRoundStatus.FUNDED
            );
        }
    }

    public List<TransactionResponseDTO> getMyDeposits() {
        User vip = userService.getPrincipal();

        List<Transaction> transactions = transactionRepo
                .findAllBySenderAndTransactionTypeOrderByCreatedAtDesc(vip, TransactionType.DEPOSIT);

        return transactionMapper.toResponseDTOList(transactions);
    }

}