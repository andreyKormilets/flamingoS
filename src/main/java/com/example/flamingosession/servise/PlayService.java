package com.example.flamingosession.servise;

import com.example.flamingosession.client.GameServiceClient;
import com.example.flamingosession.client.UiClient;
import com.example.flamingosession.domain.GameStatus;
import com.example.flamingosession.domain.Move;
import com.example.flamingosession.domain.Session;
import com.example.flamingosession.repo.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



@Component
public class PlayService {

    private static final Logger log = LoggerFactory.getLogger(PlayService.class);
    private final SessionRepository sessionRepository;

    private final GameServiceClient client;

    private final UiClient uiClient;

    Random random;

    ScheduledExecutorService scheduler;

    public PlayService(SessionRepository sessionRepository, GameServiceClient client, UiClient uiClient) {
        this.sessionRepository = sessionRepository;
        this.client = client;
        this.uiClient = uiClient;
        this.random=new Random();
        this.scheduler= Executors.newScheduledThreadPool(2);
    }

    public void simulateGame(String sessionId) {
        scheduleMove(sessionId);
    }

    public void makeMove(String sessionId) {
        log.info(sessionId);
        Session session = sessionRepository.getSession(sessionId);
        Move move = generateMove(session);
        log.info("move"+move.OX()+move.place());
        GameStatus gameStatus = client.makeMove(move);
        log.info("gameStatus"+gameStatus);
        session.addMove(move);
        uiClient.updateUiMove(move);
        if (gameStatus.completed()) {
            uiClient.updateResult(sessionId,gameStatus);
            log.info("remove");
            sessionRepository.removeCompletedGameSession(sessionId);
        } else {
            log.info("scheduleMov");
            scheduleMove(sessionId);
        }
    }

    private void scheduleMove(String sessionId) {
        scheduler.schedule(() -> makeMove(sessionId), 2, TimeUnit.SECONDS);
    }

    private Move generateMove(Session session) {
        int randomFreePlace = random.nextInt(10);
        int[] places = session.getBoard();
        log.info("board"+ Arrays.toString(places));
        int j = 0;
        for (int i = 0; i <= randomFreePlace; i++) {
            j++;
            while (places[j % 9] != 0 && j < 100) {// next free place
                j++;
            }
        }
        log.info("place "+ j% 9 );
        return new Move(j% 9, session, session.getGameStatus());
    }
}
