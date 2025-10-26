package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.RecruitmentLog;
import com.sashaprylutskyy.squidgamems.model.RefCode;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.repository.RecruitmentLogRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RecruitmentLogService {

    private final RecruitmentLogRepo recruitmentLogRepo;

    public RecruitmentLogService(RecruitmentLogRepo recruitmentLogRepo) {
        this.recruitmentLogRepo = recruitmentLogRepo;
    }

    @Transactional
    public void addLog(User player, RefCode refCode) {
        RecruitmentLog log = new RecruitmentLog(
                player,
                refCode,
                System.currentTimeMillis()
        );

        recruitmentLogRepo.save(log);
    }
}
