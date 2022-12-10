package com.eintosti.chess.game.participant;

import com.eintosti.chess.game.piece.Color;

import java.util.UUID;

public interface Participant {

    UUID getID();

    Color getColor();

    boolean isPlayer();
}