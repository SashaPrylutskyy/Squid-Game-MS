package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.Transaction;
import com.sashaprylutskyy.squidgamems.model.dto.transaction.TransactionResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CompetitionMapper.class})
public interface TransactionMapper {

    TransactionResponseDTO toResponseDTO(Transaction transaction);

}
