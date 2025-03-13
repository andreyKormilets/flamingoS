package com.example.flamingosession.controller;

import com.example.flamingosession.servise.PlayService;
import com.example.flamingosession.servise.SessionServise;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameSessionController {
    private final SessionServise sessionServise;
    private final PlayService playService;

    public GameSessionController(SessionServise sessionServise, PlayService playService) {
        this.sessionServise = sessionServise;
        this.playService = playService;
    }

    @PostMapping("/sessions")
    public String getSession() {
        return sessionServise.generateNewSession().getSessionId();
    }

    @PostMapping("/sessions/{sessionId}/simulate")
    public void simulate(@PathVariable String sessionId) {
        playService.simulateGame(sessionId);
    }
}
