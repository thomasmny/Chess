package com.eintosti.chess.game.piece.pieces;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.game.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    private final PieceType pieceType;

    public Rook(Participant participant) {
        super(participant);
        this.pieceType = PieceType.ROOK;
    }

    @Override
    public List<Move> getMoves(Board board, int x, int z) {
        List<Move> moves = new ArrayList<>();

        // N
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x, z + i)) {
                break;
            }
        }

        // E
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x + i, z)) {
                break;
            }
        }

        // S
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x, z - i)) {
                break;
            }
        }

        // W
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x - i, z)) {
                break;
            }
        }

        return moves;
    }

    @Override
    public PieceType getType() {
        return pieceType;
    }
}