package com.eintosti.chess.event;

import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.piece.Piece;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PieceEvent extends GameEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Piece piece;

    public PieceEvent(Game game, Piece piece) {
        super(game);
        this.piece = piece;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public Piece getPiece() {
        return piece;
    }
}