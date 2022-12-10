package com.eintosti.chess.listener;

import com.eintosti.chess.Chess;
import com.eintosti.chess.game.Game;
import com.eintosti.chess.manager.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WorldManipulateListener implements Listener {

    private final GameManager gameManager;

    public WorldManipulateListener(Chess plugin) {
        this.gameManager = plugin.getGameManager();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        manageWorldInteraction(event.getPlayer(), event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        manageWorldInteraction(event.getPlayer(), event);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        manageWorldInteraction(event.getPlayer(), event);
    }

    private void manageWorldInteraction(Player player, Cancellable cancellable) {
        Game game = gameManager.getGame();
        if (game == null) {
            return;
        }

        if (game.isParticipant(player.getUniqueId())) {
            cancellable.setCancelled(true);
        }
    }
}