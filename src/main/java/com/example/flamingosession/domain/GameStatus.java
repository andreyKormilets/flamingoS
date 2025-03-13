package com.example.flamingosession.domain;

public enum GameStatus {
    X_TURN,
    O_TURN,
    X_WIN,
    O_WIN,
    DRAW,
    FAILURE;

    public boolean completed() {
        return this != X_TURN && this != O_TURN;
    }
}
