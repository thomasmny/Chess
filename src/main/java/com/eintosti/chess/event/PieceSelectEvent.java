package com.eintosti.chess.event;

import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Piece;
import org.jetbrains.annotations.NotNull;

public class PieceSelectEvent extends PieceEvent {

    private final Tile tile;
    private final Participant participant;

    public PieceSelectEvent(Game game, Piece piece, Tile tile, Participant participant) {
        super(game, piece);

        this.tile = tile;
        this.participant = participant;
    }

    @NotNull
    public Participant getParticipant() {
        return participant;
    }

    @NotNull
    public Tile getTile() {
        return tile;
    }
}