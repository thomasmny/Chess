package com.eintosti.chess.game.participant;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.piece.Color;
import com.eintosti.chess.game.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Participant {

    private final UUID id;
    private final Color color;

    private Participant opponent;
    private boolean check, staleMate, checkMate;

    public Participant(UUID id, Color color) {
        this.id = id;
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public Participant getOpponent() {
        return opponent;
    }

    public void setOpponent(Participant opponent) {
        this.opponent = opponent;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isStaleMate() {
        return staleMate;
    }

    public void setStaleMate(boolean staleMate) {
        this.staleMate = staleMate;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public void setCheckMate(boolean checkMate) {
        this.checkMate = checkMate;
    }

    public List<Piece> getActivePieces(Board board) {
        List<Piece> pieces = new ArrayList<>();
        for (int x = 0; x < Board.MAX_TILES; x++) {
            for (int z = 0; z < Board.MAX_TILES; z++) {
                Piece piece = board.getTile(x, z).getPiece();
                if (piece != null && piece.getColor() == color) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    public List<Move> getLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        for (int x = 0; x < Board.MAX_TILES; x++) {
            for (int z = 0; z < Board.MAX_TILES; z++) {
                Piece piece = board.getTile(x, z).getPiece();
                if (piece != null && piece.getColor() == color) {
                    moves.addAll(piece.getMoves(board, x, z));
                }
            }
        }
        return moves;
    }
}