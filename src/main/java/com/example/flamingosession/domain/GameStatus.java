package com.example.flamingosession.domain;

public enum GameStatus {
    X_TURN,
    O_TURN,
    X_WIN,
    O_WIN,
    DRAW,
    FAILURE;

    public int intCode(){
        if(this==X_TURN){
            return 1;
        }
         if(this==O_TURN){
             return 2;
        }
         return 0;
    }
    public boolean completed(){
        return this != X_TURN && this != O_TURN;
    }
}
