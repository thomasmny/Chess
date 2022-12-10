package com.eintosti.chess.game.participant;

import com.eintosti.chess.game.piece.Color;

import java.util.UUID;

public class ComputerParticipant implements Participant {

    private final UUID id;
    private final Color color;

    public ComputerParticipant(Color color) {
        this.id = UUID.randomUUID();
        this.color = color;
    }

    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }
}