package com.eintosti.chess.event;

import com.eintosti.chess.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Game game;

    public GameEvent(Game game) {
        this.game = game;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public Game getGame() {
        return game;
    }
}