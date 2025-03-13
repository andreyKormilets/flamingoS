package com.example.flamingosession.domain;

import com.example.flamingosession.controller.dto.GameStateData;

import java.util.Arrays;
import java.util.UUID;

public class Session {
    String sessionId;
    int[] moves;
    int movesCounter;
    GameStatus gameStatus;

    public Session(UUID id) {
        this.sessionId = id.toString();
        this.gameStatus = GameStatus.X_TURN;
        this.moves = new int[9];
    }

    public void addMove(Move move) {
        moves[movesCounter] = move.place();
        movesCounter++;
        gameStatus=gameStatus==GameStatus.X_TURN?GameStatus.O_TURN:GameStatus.X_TURN;
    }

    public String getSessionId() {
        return sessionId;
    }

    public GameStateData generateGameStateData() {
        return new GameStateData(getBoard(), getMoves(), gameStatus);
    }

    public int[] getBoard() {
        int[] board = new int[9];
        for (int i = 0; i < movesCounter; i++) {
            board[moves[i]] = i % 2 + 1;
        }
        return board;

    }

    public int[] getMoves() {
        return Arrays.copyOf(moves, moves.length);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
