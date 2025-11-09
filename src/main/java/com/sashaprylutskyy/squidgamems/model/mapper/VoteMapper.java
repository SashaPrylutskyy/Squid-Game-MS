package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Vote;
import com.sashaprylutskyy.squidgamems.model.dto.vote.VoteResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface VoteMapper {

    @Mapping(source = "player", target = "user")
    @Mapping(source = "quit", target = "quit")
    VoteResponseDTO toResponseDTO(Vote vote);

    List<VoteResponseDTO> toResponseDTOList(List<Vote> votes);

}
