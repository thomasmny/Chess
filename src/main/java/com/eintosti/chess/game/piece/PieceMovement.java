package com.eintosti.chess.game.piece;

import com.eintosti.chess.Chess;
import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.board.PhysicalBoard;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.participant.PlayerParticipant;
import com.eintosti.chess.schematic.Schematic;
import com.eintosti.chess.schematic.SchematicManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class PieceMovement {

    private final Game game;
    private final Piece piece;

    private final SchematicManager schematicManager;

    public PieceMovement(Game game, Piece piece) {
        this.game = game;
        this.piece = piece;

        this.schematicManager = JavaPlugin.getPlugin(Chess.class).getSchematicManager();
    }

    public void complete(Move move, Participant participant) {
        PhysicalBoard board = game.getBoard();
        Location[] oldCorners = board.getTileCorners(board.getTile(move.getStartX(), move.getStartZ()));
        Location[] newCorners = board.getTileCorners(board.getTile(move.getEndX(), move.getEndZ()));

        Color color = piece.getColor();
        Move.Outcome outcome = board.execute(move);
        piece.setMoved();
        game.setLastMove(participant, move);

        Location pasteLocation = newCorners[0];
        switch (outcome) {
            case MOVE, TAKE -> {
                Schematic schematic = new Schematic()
                        .setPos1(oldCorners[0].clone().add(0, 1, 0))
                        .setPos2(oldCorners[1].clone().add(0, board.getHeight(), 0))
                        .setBaseLocation(oldCorners[0])
                        .saveCurrentSelection();
                schematic.paste(pasteLocation);
            }
            case PROMOTION -> schematicManager.pasteQueen(board, color, pasteLocation);
            case CASTLE -> Bukkit.broadcast(Component.text("Implement Castling")); //TODO: Castle
        }

        removeOldPiece(
                oldCorners[0].clone().add(0, 1, 0),
                oldCorners[1].clone().add(0, board.getHeight(), 0)
        );
        resetSelection(game, participant);
    }

    private void removeOldPiece(Location pos1, Location pos2) {
        World world = pos1.getWorld();
        if (world == null) {
            return;
        }

        int x1 = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int y1 = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int z1 = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int x2 = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int y2 = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int z2 = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        for (int x = x1; x < x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }
    }

    private void resetSelection(Game game, Participant participant) {
        if (participant instanceof PlayerParticipant playerParticipant) {
            game.resetTileSelection(playerParticipant.getPlayer(), true);
            playerParticipant.setSelectedTile(null);
        }
    }
}