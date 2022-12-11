package com.eintosti.chess.game.piece.pieces;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.piece.Color;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.game.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    private final PieceType pieceType;

    public Bishop(Color color) {
        super(color);
        this.pieceType = PieceType.BISHOP;
    }

    @Override
    public PieceType getType() {
        return pieceType;
    }

    @Override
    public List<Move> getMoves(Board board, int x, int z) {
        List<Move> moves = new ArrayList<>();

        // NE
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x + i, z + i)) {
                break;
            }
        }

        // NW
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x - i, z + i)) {
                break;
            }
        }

        // SE
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x + i, z - i)) {
                break;
            }
        }

        // SW
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x - i, z - i)) {
                break;
            }
        }

        return moves;
    }
}