package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.RefCode;
import com.sashaprylutskyy.squidgamems.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefCodeRepository extends JpaRepository<RefCode, Long> {

    Optional<RefCode> getByUser(User user);
}
