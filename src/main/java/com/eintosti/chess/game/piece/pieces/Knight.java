package com.eintosti.chess.game.piece.pieces;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.piece.Color;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.game.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    private final PieceType pieceType;

    public Knight(Color color) {
        super(color, 300);
        this.pieceType = PieceType.KNIGHT;
    }

    @Override
    public PieceType getType() {
        return pieceType;
    }

    @Override
    public List<Move> getMoves(Board board, int x, int z) {
        List<Move> moves = new ArrayList<>();

        addToMovesIfValid(moves, board, x, z, x + 1, z + 2); // NNE
        addToMovesIfValid(moves, board, x, z, x + 2, z + 1); // ENE
        addToMovesIfValid(moves, board, x, z, x + 2, z - 1); // ESE
        addToMovesIfValid(moves, board, x, z, x + 1, z - 2); // SSE
        addToMovesIfValid(moves, board, x, z, x - 1, z - 2); // SSW
        addToMovesIfValid(moves, board, x, z, x - 2, z - 1); // WSW
        addToMovesIfValid(moves, board, x, z, x - 2, z + 1); // WNW
        addToMovesIfValid(moves, board, x, z, x - 1, z + 2); // NNW

        return moves;
    }
}