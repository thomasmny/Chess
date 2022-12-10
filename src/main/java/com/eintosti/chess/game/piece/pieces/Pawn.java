package com.eintosti.chess.game.piece.pieces;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Color;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.game.piece.PieceType;

import java.util.ArrayList;
import java.util.List;


public class Pawn extends Piece {

    private final PieceType pieceType;

    public Pawn(Participant participant) {
        super(participant);
        this.pieceType = PieceType.PAWN;
    }

    @Override
    public PieceType getType() {
        return pieceType;
    }

    @Override
    public List<Move> getMoves(Board board, int x, int z) {
        List<Move> moves = new ArrayList<>();

        int dir = color == Color.WHITE ? 1 : -1;
        addToPawnMovesIfValid(moves, board, x, z, x, z + dir, false); // Forward
        addToPawnMovesIfValid(moves, board, x, z, x + 1, z + dir, true); // Kill diagonally
        addToPawnMovesIfValid(moves, board, x, z, x - 1, z + dir, true); // Kill diagonally

        if (!moved) {
            addToPawnMovesIfValid(moves, board, x, z, x, z + (2 * dir), false); // Forward
        }

        return moves;
    }

    /**
     * Check if the {@link Move} a {@link Pawn} wants to perform is valid.
     * <p>
     * In order for such a move to be classified as valid, either of following criteria must be met:
     * <ul>
     *   <li>The tile in front being moved to must be empty</li>
     *   <li>The tile diagonally is occupied by an opponent's piece</li>
     * </ul>
     *
     * @param board  the Board being used in the game
     * @param moves  the list of all possible moves
     * @param startX the Pawn's current tile x coordinate
     * @param startZ the Pawn's current tile z coordinate
     * @param endX   the x coordinate of the tile the Pawn wants to move to
     * @param endZ   the z coordinate of the tile the Pawn wants to move to
     * @param kill   is the Pawn's intention to kill an opponent's Piece
     */
    public void addToPawnMovesIfValid(List<Move> moves, Board board, int startX, int startZ, int endX, int endZ, boolean kill) {
        if (!onBoard(endX, endZ)) {
            return;
        }

        Tile tile = board.getTile(endX, endZ);
        Piece piece = tile.getPiece();
        if (!kill && tile.isOccupied()) {
            return;
        }

        if (kill && !tile.isOccupied()) {
            return;
        }

        if (piece == null || piece.getColor() != this.color) {
            moves.add(new Move(startX, startZ, endX, endZ));
        }
    }
}