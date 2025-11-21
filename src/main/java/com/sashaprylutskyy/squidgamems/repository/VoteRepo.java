package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {

    List<Vote> findAllByRoundId(Long roundId);

    List<Vote> findAllByRoundIdAndIsQuit(Long roundId, Boolean isQuit);

    Vote findByPlayerAndRound(User player, Round round);

    boolean existsByPlayerAndRound(User player, Round round);

    // Використовуємо EntityGraph або JOIN FETCH, щоб завантажити Player разом з Vote одним запитом
    @Query("SELECT v FROM Vote v JOIN FETCH v.player")
    List<Vote> findAllWithPlayer();
}