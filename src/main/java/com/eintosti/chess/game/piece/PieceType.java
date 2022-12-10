package com.eintosti.chess.game.piece;

public enum PieceType {
    KING("King"),
    ROOK("Rook"),
    BISHOP("Bishop"),
    QUEEN("Queen"),
    KNIGHT("Knight"),
    PAWN("Pawn");

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}