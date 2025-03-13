package com.example.flamingosession.client;

import com.example.flamingosession.client.dto.GameStateResponse;
import com.example.flamingosession.client.dto.MoveRequest;
import com.example.flamingosession.client.dto.MoveResponse;
import com.example.flamingosession.controller.dto.GameStateData;
import com.example.flamingosession.domain.GameStatus;
import com.example.flamingosession.domain.Move;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.example.flamingosession.client.ClientConfiguration.GAME_SERVICE_URL;

@Component
public class GameServiceClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public void startGame(String sessionId) {
        String url = GAME_SERVICE_URL + "/games/" + sessionId + "/start";
        restTemplate.postForEntity(url, sessionId, MoveResponse.class);
    }

    public GameStateData getGameState(String sessionId) {
        String url = GAME_SERVICE_URL + "/games/" + sessionId;
        return restTemplate.getForObject(url, GameStateResponse.class).data();

    }

    public GameStatus makeMove(Move move) {
        String url = GAME_SERVICE_URL + "/games/" +
                move.session().getSessionId() +
                "/move";
        MoveRequest request = new MoveRequest(move.OX(), move.place());
        return restTemplate.postForEntity(url, request, MoveResponse.class).getBody().moveStatus();
    }
}
