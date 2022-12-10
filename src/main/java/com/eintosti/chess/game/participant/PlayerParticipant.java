package com.eintosti.chess.game.participant;

import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.piece.Color;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerParticipant implements Participant {

    private final Color color;
    private final Player player;

    private Tile selectedTile;

    public PlayerParticipant(Color color, Player player) {
        this.color = color;
        this.player = player;
    }

    @Override
    public UUID getID() {
        return player.getUniqueId();
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public Player getPlayer() {
        return player;
    }

    @Nullable
    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
    }
}