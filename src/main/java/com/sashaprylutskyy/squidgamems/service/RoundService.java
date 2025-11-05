package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.round.RoundResponseDTO;
import com.sashaprylutskyy.squidgamems.model.mapper.RoundMapper;
import com.sashaprylutskyy.squidgamems.repository.RoundRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundService {

    private final RoundMapper roundMapper;
    private final RoundRepo roundRepo;

    public RoundService(RoundMapper roundMapper, RoundRepo roundRepo) {
        this.roundMapper = roundMapper;
        this.roundRepo = roundRepo;
    }

    @Transactional
    public RoundResponseDTO createRounds(RoundRequestDTO dto) {
        List<Round> rounds = roundMapper.toEntityList(dto);
        roundRepo.saveAll(rounds);

        return roundMapper.toResponseDTO(dto.getCompetitionId(), rounds);
    }
}
