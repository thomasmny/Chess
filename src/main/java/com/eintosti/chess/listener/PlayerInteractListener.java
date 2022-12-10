package com.eintosti.chess.listener;

import com.eintosti.chess.Chess;
import com.eintosti.chess.event.PieceMoveEvent;
import com.eintosti.chess.event.PieceSelectEvent;
import com.eintosti.chess.event.PieceUnselectEvent;
import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.participant.PlayerParticipant;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;

public class PlayerInteractListener implements Listener {

    private final GameManager gameManager;

    public PlayerInteractListener(Chess plugin) {
        this.gameManager = plugin.getGameManager();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPieceSelect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Game game = gameManager.getGame();
        if (game == null) {
            player.sendMessage("No game found. Create one with /chess create");
            return;
        }

        PlayerParticipant participant = game.getPlayerParticipant(player);
        if (participant == null || !game.isParticipantsTurn(participant)) {
            return;
        }

        World world = player.getWorld();
        RayTraceResult result = world.rayTraceBlocks(player.getLocation(), player.getLocation().getDirection(), 150);
        if (result == null) {
            return;
        }
        Block hitBlock = result.getHitBlock();
        if (hitBlock == null) {
            return;
        }

        Board board = game.getBoard();
        Tile selectedTile = board.getTile(hitBlock.getLocation());
        if (selectedTile == null) {
            return;
        }

        Tile previousTile = participant.getSelectedTile();
        if (previousTile == null) {
            Piece selectedPiece = selectedTile.getPiece();
            if (selectedPiece == null || selectedPiece.getColor() != participant.getColor()) {
                return;
            }

            participant.setSelectedTile(selectedTile);
            PieceSelectEvent pieceSelectEvent = new PieceSelectEvent(game, selectedPiece, selectedTile, participant);
            Bukkit.getServer().getPluginManager().callEvent(pieceSelectEvent);
            return;
        }

        if (selectedTile.equals(previousTile)) {
            participant.setSelectedTile(null);
            PieceUnselectEvent pieceUnselectEvent = new PieceUnselectEvent(game, selectedTile.getPiece(), selectedTile, participant);
            Bukkit.getServer().getPluginManager().callEvent(pieceUnselectEvent);
            return;
        }

        if (board.canMove(selectedTile, previousTile, participant)) {
            PieceMoveEvent pieceMoveEvent = new PieceMoveEvent(game, previousTile.getPiece(), previousTile, selectedTile, participant);
            Bukkit.getServer().getPluginManager().callEvent(pieceMoveEvent);
        }
    }
}