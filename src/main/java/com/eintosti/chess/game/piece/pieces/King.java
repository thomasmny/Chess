package com.eintosti.chess.game.piece.pieces;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.board.Tile.Name;
import com.eintosti.chess.game.piece.Color;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.game.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    private final PieceType pieceType;

    public King(Color color) {
        super(color);
        this.pieceType = PieceType.KING;
    }

    @Override
    public PieceType getType() {
        return pieceType;
    }

    @Override
    public List<Move> getMoves(Board board, int x, int z) {
        List<Move> moves = new ArrayList<>();

        addToMovesIfValid(moves, board, x, z, x, z + 1); // N
        addToMovesIfValid(moves, board, x, z, x + 1, z + 1); // NE
        addToMovesIfValid(moves, board, x, z, x + 1, z); // E
        addToMovesIfValid(moves, board, x, z, x + 1, z - 1); // SE
        addToMovesIfValid(moves, board, x, z, x, z - 1); // S
        addToMovesIfValid(moves, board, x, z, x - 1, z - 1); // SW
        addToMovesIfValid(moves, board, x, z, x - 1, z); // W
        addToMovesIfValid(moves, board, x, z, x - 1, z + 1); // NW

        return moves;
    }

    //TODO: Implement later
    private void checkCastling(Board board) {
        if (moved) {
            return;
        }

        Tile a6 = board.getTile(Name.A, 6);
        Tile a7 = board.getTile(Name.A, 7);
        if (a6.isOccupied() || a7.isOccupied()) {
            return;
        }

        Tile a8 = board.getTile(Name.A, 8);
        Piece rook = a8.getPiece();
        if (rook == null || rook.getType() != PieceType.ROOK || rook.hasMoved()) {
        }
    }
}