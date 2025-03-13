package com.example.flamingosession.client;

import com.example.flamingosession.client.dto.MoveRequest;
import com.example.flamingosession.domain.GameStatus;
import com.example.flamingosession.domain.Move;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.example.flamingosession.client.ClientConfiguration.UI_SERVICE_URL;

@Component
public class UiClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public void updateResult(String sessionId, GameStatus gameStatus) {
        String url = UI_SERVICE_URL + "/games/" + sessionId + "/status";
        try {
            restTemplate.postForEntity(url,
                    gameStatus,
                    Void.class);
        } catch (Exception e) {
            //TODO
        }
    }

    public void updateUiMove(Move move) {
        String url = UI_SERVICE_URL + "/games/" + move.session().getSessionId() + "/move";
        try {
            restTemplate.postForEntity(url,
                    new MoveRequest(move.OX(), move.place()),
                    Void.class);

        } catch (Exception e) {
            //TODO
        }
    }
}
