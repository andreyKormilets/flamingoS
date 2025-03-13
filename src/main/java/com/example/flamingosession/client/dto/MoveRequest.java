package com.example.flamingosession.client.dto;

import com.example.flamingosession.domain.GameStatus;

public record MoveRequest(GameStatus OX,
                          Integer place) {
}
