package com.eintosti.chess;

import com.eintosti.chess.command.ChessCommand;
import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.board.Tile.Name;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.listener.PieceMoveListener;
import com.eintosti.chess.listener.PieceSelectListener;
import com.eintosti.chess.listener.PlayerInteractListener;
import com.eintosti.chess.listener.WorldManipulateListener;
import com.eintosti.chess.manager.GameManager;
import com.eintosti.chess.schematic.SchematicManager;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;

import java.util.logging.Level;


public class Chess extends JavaPlugin {

    private SchematicManager schematicManager;
    private GameManager gameManager;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig().useLatestNMSVersion(true).silentLogs(true));
    }

    @Override
    public void onEnable() {
        this.schematicManager = new SchematicManager();
        this.gameManager = new GameManager(this);

        registerCommands();
        registerListeners();

        runTileInfo();

        getLogger().log(Level.INFO, "Plugin enabled");
    }

    @Override
    public void onDisable() {
        gameManager.endGame();

        getLogger().log(Level.INFO, "Plugin disabled");
    }

    private void registerCommands() {
        CommandAPI.onEnable(this);
        new ChessCommand(this);
    }

    private void registerListeners() {
        new PieceMoveListener(this);
        new PieceSelectListener(this);
        new PlayerInteractListener(this);
        new WorldManipulateListener(this);
    }

    public SchematicManager getSchematicManager() {
        return schematicManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    private void runTileInfo() {
        Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendActionBar(findLookedAtTileName(player));
        }), 0L, 3L);
    }

    private Component findLookedAtTileName(Player player) {
        Game game = gameManager.getGame();
        if (game == null) {
            return Component.empty();
        }

        World world = player.getWorld();
        RayTraceResult result = world.rayTraceBlocks(player.getLocation(), player.getLocation().getDirection(), 150);
        if (result == null) {
            return Component.empty();
        }

        Block hitBlock = result.getHitBlock();
        if (hitBlock == null) {
            return Component.empty();
        }

        Tile tile = game.getBoard().getTile(hitBlock.getLocation());
        if (tile == null) {
            return Component.empty();
        }

        Piece piece = tile.getPiece();
        String type = piece != null ? piece.getType().getName() : "-";

        return Component.text("§e" + Name.getBoardName(tile.getX(), tile.getZ()) + " §8(§7" + type + "§8)");
    }
}