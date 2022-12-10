package com.eintosti.chess.game.participant;

import com.eintosti.chess.game.piece.Color;

import java.util.UUID;

public class ComputerParticipant extends Participant {

    public ComputerParticipant(Color color) {
        super(UUID.randomUUID(), color);
    }
}