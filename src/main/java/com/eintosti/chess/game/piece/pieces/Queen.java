package com.eintosti.chess.game.piece.pieces;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.game.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    private final PieceType pieceType;

    public Queen(Participant participant) {
        super(participant);
        this.pieceType = PieceType.QUEEN;
    }

    @Override
    public PieceType getType() {
        return pieceType;
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

        // NE
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x + i, z + i)) {
                break;
            }
        }

        // E
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x + i, z)) {
                break;
            }
        }

        // SE
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x + i, z - i)) {
                break;
            }
        }

        // S
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x, z - i)) {
                break;
            }
        }

        // SW
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x - i, z - i)) {
                break;
            }
        }

        // W
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x - i, z)) {
                break;
            }
        }

        // NW
        for (int i = 1; i < Board.MAX_TILES; i++) {
            if (!addToMovesIfValid(moves, board, x, z, x - i, z + i)) {
                break;
            }
        }

        return moves;
    }
}