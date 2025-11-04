package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends JpaRepository<Game, Long> {

}
