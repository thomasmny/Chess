package com.eintosti.chess.game;

import com.eintosti.chess.Chess;
import com.eintosti.chess.ai.MiniMax;
import com.eintosti.chess.ai.MoveStrategy;
import com.eintosti.chess.event.PieceMoveEvent;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.participant.ComputerParticipant;
import com.eintosti.chess.game.participant.PlayerParticipant;
import com.eintosti.chess.game.piece.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SinglePlayerGame extends Game {

    public SinglePlayerGame(Player player, Location pos1, Location pos2) {
        super(new PlayerParticipant(Color.WHITE, player), new ComputerParticipant(Color.BLACK), pos1, pos2);
    }

    @Override
    public PlayerParticipant getWhite() {
        return (PlayerParticipant) white;
    }

    @Override
    public ComputerParticipant getBlack() {
        return (ComputerParticipant) black;
    }

    @Override
    public void nextTurn() {
        super.nextTurn();

        if (!isParticipantsTurn(black)) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(Chess.class), () -> {
            MoveStrategy strategy = new MiniMax(this, 3);
            Move aiMove = strategy.execute(board);
            Tile currentTile = board.getTile(aiMove.getStartX(), aiMove.getStartZ());
            Tile tileToMoveTo = board.getTile(aiMove.getEndX(), aiMove.getEndZ());

            PieceMoveEvent pieceMoveEvent = new PieceMoveEvent(this, currentTile.getPiece(), currentTile, tileToMoveTo, black);
            Bukkit.getServer().getPluginManager().callEvent(pieceMoveEvent);
        }, 20L);
    }
}