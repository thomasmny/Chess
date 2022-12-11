package com.eintosti.chess.listener;

import com.eintosti.chess.Chess;
import com.eintosti.chess.event.PieceMoveEvent;
import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.piece.PieceMovement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PieceMoveListener implements Listener {

    public PieceMoveListener(Chess plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPieceMove(PieceMoveEvent event) {
        Game game = event.getGame();
        Move move = event.getMove();

        new PieceMovement(game, event.getPiece()).execute(move);
        game.nextTurn();
    }
}