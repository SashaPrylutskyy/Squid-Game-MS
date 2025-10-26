package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository repo;

    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }

    public Role getRoleById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public Long getRoleIdByTitle(String title) {
        Role role = repo.findByRoleTitle(title)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return role.getId();
    }
}
