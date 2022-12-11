package com.eintosti.chess.event;

import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Piece;
import org.jetbrains.annotations.NotNull;

public class PieceMoveEvent extends PieceEvent {

    private final Tile oldTile, newTile;
    private final Participant participant;

    public PieceMoveEvent(Game game, Piece piece, Tile oldTile, Tile newTile, Participant participant) {
        super(game, piece);

        this.newTile = newTile;
        this.oldTile = oldTile;
        this.participant = participant;
    }

    @NotNull
    public Participant getParticipant() {
        return participant;
    }

    @NotNull
    public Tile getOldTile() {
        return oldTile;
    }

    @NotNull
    public Tile getNewTile() {
        return newTile;
    }

    public Move getMove() {
        return new Move(getPiece(), oldTile.getX(), oldTile.getZ(), newTile.getX(), newTile.getZ());
    }
}