package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Lobby;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.repository.LobbyRepository;
import org.springframework.stereotype.Service;

@Service
public class LobbyService {

    private final LobbyRepository lobbyRepo;

    public LobbyService(LobbyRepository lobbyRepo) {
        this.lobbyRepo = lobbyRepo;
    }

    public void assignUserToLobby(User user, Long lobbyId) {
        Lobby lobby = new Lobby(
                lobbyId,
                user,
                System.currentTimeMillis()
        );
        lobbyRepo.save(lobby);
    }
}
