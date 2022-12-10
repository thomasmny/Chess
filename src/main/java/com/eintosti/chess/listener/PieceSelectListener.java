package com.eintosti.chess.listener;

import com.eintosti.chess.Chess;
import com.eintosti.chess.event.PieceSelectEvent;
import com.eintosti.chess.event.PieceUnselectEvent;
import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.participant.PlayerParticipant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PieceSelectListener implements Listener {

    public PieceSelectListener(Chess plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPieceSelect(PieceSelectEvent event) {
        if (!(event.getParticipant() instanceof PlayerParticipant playerParticipant)) {
            return;
        }

        Player player = playerParticipant.getPlayer();
        Tile tile = event.getTile();
        Game game = event.getGame();
        game.resetTileSelection(player, false);
        game.showSelectedTile(player, tile);
        game.showPossibleMoves(player, tile, event.getPiece());
    }

    @EventHandler
    public void onPieceUnselect(PieceUnselectEvent event) {
        if (!(event.getParticipant() instanceof PlayerParticipant playerParticipant)) {
            return;
        }

        Player player = playerParticipant.getPlayer();
        Game game = event.getGame();
        game.resetTileSelection(player, false);
    }
}