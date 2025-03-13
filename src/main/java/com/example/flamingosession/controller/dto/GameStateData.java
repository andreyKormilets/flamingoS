package com.example.flamingosession.controller.dto;

import com.example.flamingosession.domain.GameStatus;


public record GameStateData(int [] board , int [] moves, GameStatus gameStatus){
}