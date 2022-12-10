package com.eintosti.chess.command;

import com.eintosti.chess.Chess;
import com.eintosti.chess.game.Game;
import com.eintosti.chess.manager.GameManager;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.LocationArgument;
import dev.jorel.commandapi.arguments.LocationType;
import org.bukkit.Location;

public class ChessCommand {

    private final GameManager gameManager;

    public ChessCommand(Chess plugin) {
        this.gameManager = plugin.getGameManager();
        registerChessCommand();
    }

    private void registerChessCommand() {
        new CommandAPICommand("chess")
                .withSubcommand(new CommandAPICommand("create")
                        .withArguments(new LocationArgument("start", LocationType.BLOCK_POSITION))
                        .withArguments(new LocationArgument("end", LocationType.BLOCK_POSITION))
                        .executesPlayer((player, args) -> {
                            Location start = (Location) args[0];
                            Location end = ((Location) args[1]).add(0, 15, 0); //TODO: Customizable height

                            if (gameManager.getGame() != null) {
                                gameManager.endGame();
                            }
                            gameManager.createSinglePlayerGame(player, start, end);
                            player.sendMessage("§aCreated game");
                        }))
                .withSubcommand(new CommandAPICommand("end")
                        .executesPlayer((player, args) -> {
                            Game game = gameManager.getGame();
                            if (game == null) {
                                player.sendMessage("§cGame not found");
                                return;
                            }

                            player.sendMessage("§b§oResetting board...");
                            gameManager.endGame();
                        }))
                .register();
    }
}