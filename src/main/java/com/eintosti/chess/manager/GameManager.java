package com.eintosti.chess.manager;

import com.eintosti.chess.Chess;
import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.SinglePlayerGame;
import com.eintosti.chess.game.participant.PlayerParticipant;
import com.eintosti.chess.schematic.SchematicManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GameManager {

    private final SchematicManager schematicManager;

    private Game game;

    public GameManager(Chess plugin) {
        this.schematicManager = plugin.getSchematicManager();
    }

    public void createSinglePlayerGame(Player player, Location pos1, Location pos2) {
        this.game = new SinglePlayerGame(player, pos1, pos2);

        schematicManager.saveQueens(game.getBoard());
    }

    public void creatMultiPlayerGame(Player playerA, Player playerB, Location pos1, Location pos2) {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Game getGame() {
        return game;
    }

    public void endGame() {
        game.resetBoard();

        if (game.getWhite() instanceof PlayerParticipant playerParticipant) {
            game.resetTileSelection(playerParticipant.getPlayer(), true);
        }
        if (game.getBlack() instanceof PlayerParticipant playerParticipant) {
            game.resetTileSelection(playerParticipant.getPlayer(), true);
        }

        this.game = null;
    }
}