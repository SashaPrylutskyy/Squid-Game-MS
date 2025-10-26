package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.RefCode;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.refCode.RefCodeSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.mapper.RefCodeMapper;
import com.sashaprylutskyy.squidgamems.repository.RefCodeRepository;
import com.sashaprylutskyy.squidgamems.util.PasswordGenerator;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

@Service
public class RefCodeService {

    private final RefCodeRepository refCodeRepo;
    private final RefCodeMapper refCodeMapper;
    private final UserService userService;

    public RefCodeService(RefCodeRepository refCodeRepo, RefCodeMapper refCodeMapper,
                          UserService userService) {
        this.refCodeRepo = refCodeRepo;
        this.refCodeMapper = refCodeMapper;
        this.userService = userService;
    }

    public void assignRefCode(User salesman) {
        RefCode refCode = new RefCode(
                PasswordGenerator.generate(8),
                salesman
        );
        refCodeRepo.save(refCode);
    }

    private RefCode getRefCode(User user) {
        return refCodeRepo.getByUser(user)
                .orElseThrow(() -> new NoResultException("User %s doesn't have a referral code."
                        .formatted(user.getEmail())));
    }

    public RefCodeSummaryDTO getRefCode() {
        User principal = userService.getPrincipal();
        RefCode refCode = getRefCode(principal);

        return refCodeMapper.toSummaryDTO(refCode);
    }
}
