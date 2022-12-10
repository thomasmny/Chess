package com.eintosti.chess.schematic;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.board.Tile.Name;
import com.eintosti.chess.game.piece.Color;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class SchematicManager {

    private final Map<Board, Schematic> blackQueens;
    private final Map<Board, Schematic> whiteQueens;

    public SchematicManager() {
        this.blackQueens = new HashMap<>();
        this.whiteQueens = new HashMap<>();
    }

    public void saveQueens(Board board) {
        saveQueen(board, Color.WHITE);
        saveQueen(board, Color.BLACK);
    }

    private void saveQueen(Board board, Color color) {
        Tile tile = switch (color) {
            case WHITE -> board.getTile(Name.D, 1);
            case BLACK -> board.getTile(Name.D, 8);
        };

        Location[] tileCorners = board.getTileCorners(tile);
        Schematic queen = new Schematic()
                .setPos1(tileCorners[0].clone().add(0, 1, 0))
                .setPos2(tileCorners[1].clone().add(0, board.getHeight(), 0))
                .setBaseLocation(tileCorners[0])
                .saveCurrentSelection();

        switch (color) {
            case WHITE -> this.whiteQueens.put(board, queen);
            case BLACK -> this.blackQueens.put(board, queen);
        }
    }

    public void pasteQueen(Board board, Color color, Location pasteLocation) {
        Schematic schematic = switch (color) {
            case BLACK -> blackQueens.get(board);
            case WHITE -> whiteQueens.get(board);
        };
        schematic.paste(pasteLocation);
    }
}