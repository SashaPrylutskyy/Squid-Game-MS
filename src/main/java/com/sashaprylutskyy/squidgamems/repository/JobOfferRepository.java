package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.JobOffer;
import com.sashaprylutskyy.squidgamems.model.enums.JobOfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    Optional<JobOffer> findByToken(UUID token);

    List<JobOffer> findAllByLobbyId(Long lobbyId);

    List<JobOffer> findAllByLobbyIdAndOfferStatus(Long lobbyId, JobOfferStatus offerStatus);
}
