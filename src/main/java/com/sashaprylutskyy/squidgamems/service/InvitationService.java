package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.invitation.InvitationRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.invitation.InvitationResponseDTO;
import com.sashaprylutskyy.squidgamems.model.enums.InvitationStatus;
import com.sashaprylutskyy.squidgamems.model.mapper.InvitationMapper;
import com.sashaprylutskyy.squidgamems.repository.InvitationRepository;
import com.sashaprylutskyy.squidgamems.model.Invitation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationService {


    private final InvitationRepository invitationRepo;
    private final UserService userService;
    private final RoleService roleService;
    private final InvitationMapper invitationMapper;


    public InvitationService(InvitationRepository invitationRepo, UserService userService,
                             RoleService roleService, InvitationMapper invitationMapper) {
        this.invitationRepo = invitationRepo;
        this.userService = userService;
        this.roleService = roleService;
        this.invitationMapper = invitationMapper;
    }

    //todo implement invitation for other then HOST. For the time being this is a problem cuz it's difficult to catch to which lobby does a person belongs to
    public InvitationResponseDTO makeJobOffer(InvitationRequestDTO dto) {
        User principal = userService.getPrincipal();
        Role role = roleService.getRoleById(dto.getRoleId());

        if (role.toString().equals("HOST") || role.toString().equals("VIP") || role.toString().equals("PLAYER")) {
            throw new RuntimeException("Unable to make a job offer neither for VIP nor HOST nor PLAYER");
        }

        Invitation invitation = new Invitation(
                principal.getId(),
                principal,
                role,
                dto.getEmail(),
                UUID.randomUUID(),
                InvitationStatus.AWAITING,
                System.currentTimeMillis()
        );
        invitationRepo.save(invitation);
        return invitationMapper.toResponseDTO(invitation);
    }
}
