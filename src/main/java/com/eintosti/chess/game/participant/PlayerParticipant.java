package com.eintosti.chess.game.participant;

import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.piece.Color;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerParticipant extends Participant {

    private final Player player;
    private Tile selectedTile;

    public PlayerParticipant(Color color, Player player) {
        super(player.getUniqueId(), color);
        this.player = player;
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