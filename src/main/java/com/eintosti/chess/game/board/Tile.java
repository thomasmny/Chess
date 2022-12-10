package com.eintosti.chess.game.board;

import com.eintosti.chess.game.piece.Piece;
import org.jetbrains.annotations.Nullable;

public class Tile {

    private final int x;
    private final int z;

    private Piece piece;
    private boolean occupied;

    public Tile(int x, int z) {
        this.x = x;
        this.z = z;

        this.piece = null;
        this.occupied = false;
    }

    public Tile(int x, int z, Piece piece) {
        this.x = x;
        this.z = z;

        this.piece = piece;
        this.occupied = true;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public String toString() {
        return String.valueOf((char) ('A' + x)) + (z + 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tile other)) {
            return false;
        }

        return other.x == x && other.z == z;
    }

    public enum Name {
        A(0),
        B(1),
        C(2),
        D(3),
        E(4),
        F(5),
        G(6),
        H(7);

        private final int num;

        Name(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        @Nullable
        public static String getBoardName(int x, int z) {
            for (Name name : values()) {
                if (name.getNum() == x) {
                    return name.name() + (z + 1);
                }
            }
            return null;
        }
    }
}