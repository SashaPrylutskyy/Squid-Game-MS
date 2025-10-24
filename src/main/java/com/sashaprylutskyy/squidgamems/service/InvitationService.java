package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.InvitationRequestDTO;
import com.sashaprylutskyy.squidgamems.model.enums.InvitationStatus;
import com.sashaprylutskyy.squidgamems.repository.InvitationRepository;
import com.sashaprylutskyy.squidgamems.model.Invitation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationService {


    private final InvitationRepository invitationRepo;
    private final UserService userService;
    private final RoleService roleService;

    public InvitationService(InvitationRepository invitationRepo, UserService userService, RoleService roleService) {
        this.invitationRepo = invitationRepo;
        this.userService = userService;
        this.roleService = roleService;
    }

    //todo implement invitation for other then HOST. For the time being this is a problem cuz it's difficult to catch to which lobby does a person belongs to
    public Invitation create(InvitationRequestDTO dto) {
        User principal = userService.getPrincipal();
        Role role = roleService.getRoleById(dto.getRoleId());

        if (role.toString().equals("Host") || role.toString().equals("VIP")) {
            throw new RuntimeException("Unable to invite neither VIP nor HOST");
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

        return invitationRepo.save(invitation);
    }
}
