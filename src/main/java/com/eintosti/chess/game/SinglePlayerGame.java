package com.eintosti.chess.game;

import com.eintosti.chess.Chess;
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

import java.util.List;
import java.util.Random;

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
            Move randomMove = getRandomMove();
            Tile currentTile = board.getTile(randomMove.getStartX(), randomMove.getStartZ());
            Tile tileToMoveTo = board.getTile(randomMove.getEndX(), randomMove.getEndZ());

            PieceMoveEvent pieceMoveEvent = new PieceMoveEvent(this, currentTile.getPiece(), currentTile, tileToMoveTo, black);
            Bukkit.getServer().getPluginManager().callEvent(pieceMoveEvent);

            Bukkit.broadcastMessage(randomMove.toString());
        }, 20L);
    }

    private Move getRandomMove() {
        List<Move> moves = black.getLegalMoves(board);
        int index = new Random().nextInt(moves.size());
        return moves.get(index);
    }
}