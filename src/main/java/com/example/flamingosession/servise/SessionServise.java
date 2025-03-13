package com.example.flamingosession.servise;

import com.example.flamingosession.client.GameServiceClient;
import com.example.flamingosession.controller.dto.GameStateData;
import com.example.flamingosession.domain.Session;
import com.example.flamingosession.repo.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionServise {
    private final SessionRepository sessionRepository;
    private final GameServiceClient client;

    public SessionServise(SessionRepository sessionRepository, GameServiceClient client) {
        this.sessionRepository = sessionRepository;
        this.client = client;
    }

    public Session generateNewSession() {
        UUID uuid = UUID.randomUUID();
        Session session = new Session(uuid);
        sessionRepository.putNewSession(session);
        client.startGame(session.getSessionId());
        return session;
    }

    public GameStateData getGameSessionState(String sessionId) {
        GameStateData gameStateData;
        Session session;
        session = sessionRepository.getSession(sessionId);
        if (session != null) {
            gameStateData = session.generateGameStateData();
        } else {
            gameStateData = client.getGameState(sessionId);
        }
        return gameStateData;
    }
}
