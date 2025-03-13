package com.example.flamingosession.controller;

import com.example.flamingosession.client.dto.GameStateResponse;
import com.example.flamingosession.servise.SessionServise;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameHistoryController {

    private final SessionServise sessionServise;

    public GameHistoryController(SessionServise sessionServise) {
        this.sessionServise = sessionServise;
    }

    @GetMapping("/sessions/{sessionId}")
    public GameStateResponse getGameState(@PathVariable String sessionId) {
        return new GameStateResponse(sessionServise.getGameSessionState(sessionId));
    }
}
