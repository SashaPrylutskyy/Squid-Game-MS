package com.sashaprylutskyy.squidgamems.repository;


import com.sashaprylutskyy.squidgamems.model.RecruitmentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentLogRepo extends JpaRepository<RecruitmentLog, Long> {

}
