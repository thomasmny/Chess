package com.eintosti.chess.event;

import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Piece;
import org.jetbrains.annotations.NotNull;

public class PieceMoveEvent extends PieceEvent {

    private final Tile from, to;
    private final Participant participant;

    public PieceMoveEvent(Game game, Move move, Participant participant) {
        super(game, move.getPiece());

        this.from = move.getFrom();
        this.to = move.getTo();
        this.participant = participant;
    }

    public PieceMoveEvent(Game game, Piece piece, Tile from, Tile to, Participant participant) {
        super(game, piece);

        this.to = to;
        this.from = from;
        this.participant = participant;
    }

    @NotNull
    public Participant getParticipant() {
        return participant;
    }

    @NotNull
    public Tile getFrom() {
        return from;
    }

    @NotNull
    public Tile getTo() {
        return to;
    }

    public Move getMove() {
        return new Move(getPiece(), from, to);
    }
}