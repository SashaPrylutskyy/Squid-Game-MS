package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Competition;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.Vote;
import com.sashaprylutskyy.squidgamems.model.dto.reports.*;
import com.sashaprylutskyy.squidgamems.model.enums.TransactionType;
import com.sashaprylutskyy.squidgamems.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private final UserRepository userRepository;
    private final CompetitionRepo competitionRepo;
    private final RoundRepo roundRepo;
    private final RoundResultRepo roundResultRepo;
    private final VoteRepo voteRepo;
    private final TransactionRepo transactionRepo;
    private final AssignmentRepository assignmentRepository;
    private final RecruitmentLogRepo recruitmentLogRepo;

    public ReportService(UserRepository userRepository, CompetitionRepo competitionRepo, RoundRepo roundRepo,
            RoundResultRepo roundResultRepo, VoteRepo voteRepo, TransactionRepo transactionRepo,
            AssignmentRepository assignmentRepository, RecruitmentLogRepo recruitmentLogRepo) {
        this.userRepository = userRepository;
        this.competitionRepo = competitionRepo;
        this.roundRepo = roundRepo;
        this.roundResultRepo = roundResultRepo;
        this.voteRepo = voteRepo;
        this.transactionRepo = transactionRepo;
        this.assignmentRepository = assignmentRepository;
        this.recruitmentLogRepo = recruitmentLogRepo;
    }

    public List<PlayerStatsDTO> getPlayerStatistics() {
        return userRepository.getPlayerStatistics();
    }

    public CompetitionStatsDTO getCompetitionDetails(Long competitionId) {
        // Перевірка існування - ок, але можна обійтися без неї, якщо бізнес-логіка дозволяє
        if (!competitionRepo.existsById(competitionId)) {
            throw new RuntimeException("Competition not found with id: " + competitionId);
        }

        // FIX: Обробка null, якщо транзакцій немає
        Long totalPrizePool = Optional.ofNullable(
                transactionRepo.getTotalAmountByCompetitionIdAndType(competitionId, TransactionType.DEPOSIT)
        ).orElse(0L);

        Long vipContributions = Optional.ofNullable(
                transactionRepo.getVipContributionsByCompetitionId(competitionId)
        ).orElse(0L);

        List<RoundStatsDTO> rounds = roundRepo.getRoundStatsByCompetitionId(competitionId);

        return new CompetitionStatsDTO(competitionId, totalPrizePool, vipContributions, rounds);
    }

    public List<RoundStatsDTO> getTopHardestRounds() {
        return roundRepo.findTopHardestRounds(org.springframework.data.domain.PageRequest.of(0, 10));
    }

    /**
     * Аналіз голосування.
     * УВАГА: Для великих обсягів даних цю логіку треба переносити в SQL (GROUP BY).
     * Зараз код оптимізовано для читабельності та уникнення простих помилок.
     */
    public VotingStatsDTO getVotingAnalysis() {
        // Цей метод в репозиторії ОБОВ'ЯЗКОВО має мати @EntityGraph або JOIN FETCH user
        List<Vote> votes = voteRepo.findAllWithPlayer();

        long totalVotes = votes.size();
        if (totalVotes == 0) {
            return new VotingStatsDTO(0, 0.0, 0.0, Map.of());
        }

        long quitCount = votes.stream().filter(Vote::getQuit).count();

        // Ініціалізація структури для демографії
        Map<String, Map<String, Integer>> byDemographics = initializeDemographicsMap();

        LocalDate now = LocalDate.now();

        for (Vote vote : votes) {
            User player = vote.getPlayer();
            String choice = Boolean.TRUE.equals(vote.getQuit()) ? "QUIT" : "CONTINUE";

            // 1. Групування по статі
            if (player.getSex() != null) {
                String sexKey = "sex_" + player.getSex().name();
                incrementStat(byDemographics, sexKey, choice);
            }

            // 2. Групування по віку
            if (player.getBirthday() != null) {
                // Конвертація java.util.Date -> java.time.LocalDate
                LocalDate birthday = player.getBirthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                String ageKey = getAgeCategory(birthday, now);
                incrementStat(byDemographics, ageKey, choice);
            }
        }

        return new VotingStatsDTO(
                totalVotes,
                (double) quitCount / totalVotes,
                (double) (totalVotes - quitCount) / totalVotes,
                byDemographics
        );
    }

    public List<VipStatsDTO> getVipRating() {
        return transactionRepo.getVipStats();
    }

    public List<StaffStatsDTO> getStaffEfficiency() {
        return roundResultRepo.getStaffStats();
    }

    public List<RecruitmentStatsDTO> getRecruitmentStats() {
        return recruitmentLogRepo.getRecruitmentStats();
    }

    // --- Private Helpers для чистоти коду ---

    private Map<String, Map<String, Integer>> initializeDemographicsMap() {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        List<String> keys = List.of(
                "age_18_25", "age_26_40", "age_41_60", "age_60_plus",
                "sex_MALE", "sex_FEMALE"
        );

        for (String key : keys) {
            Map<String, Integer> stats = new HashMap<>();
            stats.put("QUIT", 0);
            stats.put("CONTINUE", 0);
            map.put(key, stats);
        }
        return map;
    }

    private String getAgeCategory(LocalDate birthday, LocalDate now) {
        int age = Period.between(birthday, now).getYears();
        if (age >= 18 && age <= 25) return "age_18_25";
        if (age >= 26 && age <= 40) return "age_26_40";
        if (age >= 41 && age <= 60) return "age_41_60";
        return "age_60_plus";
    }

    private void incrementStat(Map<String, Map<String, Integer>> demographics, String category, String choice) {
        if (demographics.containsKey(category)) {
            demographics.get(category).merge(choice, 1, Integer::sum);
        }
    }
}
