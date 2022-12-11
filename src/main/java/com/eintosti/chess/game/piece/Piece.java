package com.eintosti.chess.game.piece;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.board.Tile;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Piece {

    protected final Color color;
    protected final int value;
    protected boolean moved;

    public Piece(Color color, int value) {
        this.color = color;
        this.value = value;
        this.moved = false;
    }

    /**
     * Gets the type of the piece.
     *
     * @return The piece type
     */
    public abstract PieceType getType();

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved() {
        this.moved = true;
    }

    /**
     * Gets a list of all valid move the piece can make.
     *
     * @param board The board being used in the game
     * @param x     The x coordinate of the piece's tile
     * @param z     The z coordinate of the piece's tile
     * @return A list of all valid moves
     */
    public abstract List<Move> getMoves(Board board, int x, int z);

    /**
     * Gets the move a Piece is trying to perform
     *
     * @param startX The piece's current tile x-coordinate
     * @param startZ The piece's current tile z-coordinate
     * @param endX   The x-coordinate of the tile the piece wants to move to
     * @param endZ   The z-coordinate of the tile the piece wants to move to
     * @return The equivalent move, if any
     */
    @Nullable
    public Move getMove(Board board, int startX, int startZ, int endX, int endZ) {
        for (Move move : getMoves(board, startX, startZ)) {
            if (move.equals(new Move(this, board.getTile(startX, startZ), board.getTile(endX, endZ)))) {
                return move;
            }
        }
        return null;
    }

    /**
     * Checks if the {@link Move} a {@link Piece} wants to perform is valid.
     * <p>
     * In order for such a move to be classified as valid, either of following criteria must be met:
     * <ul>
     *   <li>The tile being moved to must be empty</li>
     *   <li>The tile being moved is occupied by an opponent's piece</li>
     * </ul>
     *
     * @param moves  The list of all possible moves
     * @param board  The board being used in the game
     * @param startX The current tile x-coordinate
     * @param startZ The current tile z-coordinate
     * @param endX   The x-coordinate of the tile the piece wants to move to
     * @param endZ   The z-coordinate of the tile the piece wants to move to
     * @return {@code true} if the iteration of possible moves should continue
     */
    public boolean addToMovesIfValid(List<Move> moves, Board board, int startX, int startZ, int endX, int endZ) {
        if (!onBoard(endX, endZ)) {
            return false;
        }

        Tile end = board.getTile(endX, endZ);
        Piece piece = end.getPiece();
        Move move = new Move(this, board.getTile(startX, startZ), end);

        if (piece == null) {
            moves.add(move);
            return true;
        }

        if (piece.color != this.color) {
            moves.add(move);
        }
        return false;
    }

    /**
     * Check if the move a Piece is trying to perform is valid
     *
     * @param startX the Piece's current x coordinate
     * @param startZ the Piece's current z coordinate
     * @param endX   the x coordinate of the tile the Piece wants to move to
     * @param endZ   the z coordinate of the tile the Piece wants to move to
     * @return true if the move is valid, otherwise false
     */
    public boolean isValidMove(Board board, int startX, int startZ, int endX, int endZ) {
        return getMove(board, startX, startZ, endX, endZ) != null;
    }

    /**
     * Checks if the tile is on the board.
     *
     * @param x The x location
     * @param z The z location
     * @return {@code true} if the coordinates are located on the board, otherwise {@code false}
     */
    public boolean onBoard(int x, int z) {
        return x >= 0 && x <= 7 && z >= 0 && z <= 7;
    }
}