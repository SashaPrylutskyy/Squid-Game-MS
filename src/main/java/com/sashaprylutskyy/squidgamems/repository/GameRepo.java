package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends JpaRepository<Game, Long> {

    List<Game> findAllByIdIn(List<Long> ids);
}
