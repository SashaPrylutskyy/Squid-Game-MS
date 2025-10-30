package com.sashaprylutskyy.squidgamems.model.mapper;

import com.sashaprylutskyy.squidgamems.model.JobOffer;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.jobOffer.JobOfferSummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobOfferMapper {

    JobOfferResponseDTO toResponseDTO(JobOffer jobOffer);

    JobOfferSummaryDTO toSummaryDTO(JobOffer jobOffer);

}
