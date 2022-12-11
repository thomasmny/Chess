package com.eintosti.chess.game.participant;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.piece.Color;
import com.eintosti.chess.game.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Participant {

    private final UUID id;
    private final Color color;

    public Participant(UUID id, Color color) {
        this.id = id;
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public List<Move> getLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        for (int x = 0; x < Board.MAX_TILES; x++) {
            for (int z = 0; z < Board.MAX_TILES; z++) {
                Piece piece = board.getTile(x, z).getPiece();
                if (piece != null && piece.getColor() == color) {
                    moves.addAll(piece.getMoves(board, x, z));
                }
            }
        }
        return moves;
    }
}