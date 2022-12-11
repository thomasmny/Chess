package com.eintosti.chess.game.board;

import com.eintosti.chess.game.piece.Piece;

public class Move {

    private final Piece piece;
    private final int startX, startZ;
    private final int endX, endZ;
    private final boolean castling;

    public Move(Piece piece, int startX, int startZ, int endX, int endZ) {
        this.piece = piece;
        this.startX = startX;
        this.startZ = startZ;
        this.endX = endX;
        this.endZ = endZ;
        this.castling = false;
    }

    public Move(Piece piece, int startX, int startZ, int endX, int endZ, boolean castling) {
        this.piece = piece;
        this.startX = startX;
        this.startZ = startZ;
        this.endX = endX;
        this.endZ = endZ;
        this.castling = castling;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartZ() {
        return startZ;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndZ() {
        return endZ;
    }

    public boolean isCastling() {
        return castling;
    }

    @Override
    public String toString() {
        String from = String.valueOf((char) ('A' + startX)) + (startZ + 1);
        String to = String.valueOf((char) ('A' + endX)) + (endZ + 1);
        return from + " -> " + to;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Move move)) {
            return false;
        }

        return move.getStartX() == startX && move.getStartZ() == startZ && move.getEndX() == endX && move.getEndZ() == endZ;
    }

    public enum Outcome {

        /**
         * A piece moves to an empty tile.
         */
        MOVE(true),

        /**
         * A move which is not allowed to be performed.
         */
        ILLEGAL_MOVE(false),

        /**
         * A piece takes an opponent's piece.
         */
        TAKE(true),

        /**
         * A move which would leave the participant in check.
         */
        LEAVES_IN_CHECK(false),

        /**
         * The move was a castle. During castling, the king is transferred two squares toward a rook of the same color on
         * the same rank, and the rook is transferred to the square crossed by the king.
         */
        CASTLE(true),

        /**
         * A pawn is moved to its last rank and is promoted to a queen.
         * Currently, it is not possible to underpromote to another piece besides the queen.
         */
        PROMOTION(true);

        private final boolean done;

        Outcome(boolean done) {
            this.done = done;
        }

        public boolean isDone() {
            return done;
        }
    }
}