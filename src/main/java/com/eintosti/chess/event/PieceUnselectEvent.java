package com.eintosti.chess.event;

import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Piece;
import org.jetbrains.annotations.NotNull;

public class PieceUnselectEvent extends PieceEvent {

    private final Tile tileLocation;
    private final Participant participant;

    public PieceUnselectEvent(Game game, Piece piece, Tile tileLocation, Participant participant) {
        super(game, piece);
        this.tileLocation = tileLocation;
        this.participant = participant;
    }

    @NotNull
    public Tile getTile() {
        return tileLocation;
    }

    public Participant getParticipant() {
        return participant;
    }
}