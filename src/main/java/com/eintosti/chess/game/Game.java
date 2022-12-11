package com.eintosti.chess.game;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.board.PhysicalBoard;
import com.eintosti.chess.game.board.Tile;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.participant.PlayerParticipant;
import com.eintosti.chess.game.piece.Piece;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public abstract class Game {

    protected final Participant white;
    protected final Participant black;
    protected final PhysicalBoard board;

    private Participant turn;
    private LastMove lastMove;

    public Game(Participant white, Participant black, Location pos1, Location pos2) {
        this.white = white;
        this.black = black;
        this.board = new PhysicalBoard(pos1, pos2);

        white.setOpponent(black);
        black.setOpponent(white);

        this.turn = white;
    }

    public Participant getWhite() {
        return white;
    }

    public Participant getBlack() {
        return black;
    }

    @Nullable
    public PlayerParticipant getPlayerParticipant(Player player) {
        if (white.getId().equals(player.getUniqueId())) {
            return (PlayerParticipant) white;
        }

        if (black.getId().equals(player.getUniqueId())) {
            return (PlayerParticipant) black;
        }

        return null;
    }

    public boolean isParticipant(UUID uuid) {
        return white.getId().equals(uuid) || black.getId().equals(uuid);
    }

    public boolean isParticipantsTurn(Participant participant) {
        return participant.getColor() == turn.getColor();
    }

    public Participant getCurrentTurn() {
        return turn;
    }

    public void nextTurn() {
        this.turn = turn.getOpponent();
        showLastMoveToOpponent();
    }

    public void setLastMove(Participant participant, Move move) {
        this.lastMove = new LastMove(participant, move);
    }

    public PhysicalBoard getBoard() {
        return board;
    }

    public void resetBoard() {
        board.getSchematic().paste(board.getBaseLocation());
    }

    public void resetTileSelection(Player player, boolean resetLastMove) {
        for (int x = 0; x < Board.MAX_TILES; x++) {
            for (int z = 0; z < Board.MAX_TILES; z++) {
                Tile tile = board.getTile(x, z);
                if (!resetLastMove && lastMove != null) {
                    Move last = lastMove.getMove();
                    Tile from = board.getTile(last.getStartX(), last.getStartZ());
                    Tile to = board.getTile(last.getEndX(), last.getEndZ());
                    if (tile.equals(from) || tile.equals(to)) {
                        continue;
                    }
                }
                board.getTileFloor(tile).forEach(block -> player.sendBlockChange(block.getLocation(), block.getBlockData()));
            }
        }
        if (!resetLastMove) {
            showLastMoveToOpponent();
        }
    }

    private void colorTileFloor(Player player, PhysicalBoard board, Tile tile, Material glass, Material concrete) {
        for (Block block : board.getTileFloor(tile)) {
            player.sendBlockChange(block.getLocation(), glass.createBlockData());
            player.sendBlockChange(block.getRelative(BlockFace.DOWN).getLocation(), concrete.createBlockData());
        }
    }

    public void showSelectedTile(Player player, Tile tile) {
        colorTileFloor(player, board, tile, Material.RED_STAINED_GLASS, Material.RED_CONCRETE);
    }

    public void showPossibleMoves(Player player, Tile tile, Piece piece) {
        List<Move> moves = piece.getMoves(board, tile.getX(), tile.getZ());
        for (Move move : moves) {
            colorTileFloor(player, board, board.getTile(move.getEndX(), move.getEndZ()), Material.LIME_STAINED_GLASS, Material.LIME_CONCRETE);
        }
    }

    public void showLastMoveToOpponent() {
        if (lastMove == null || !(lastMove.getParticipant().getOpponent() instanceof PlayerParticipant playerParticipant)) {
            return;
        }

        Player player = playerParticipant.getPlayer();
        Move move = lastMove.getMove();
        colorTileFloor(player, board, board.getTile(move.getStartX(), move.getStartZ()), Material.YELLOW_STAINED_GLASS, Material.YELLOW_CONCRETE);
        colorTileFloor(player, board, board.getTile(move.getEndX(), move.getEndZ()), Material.YELLOW_STAINED_GLASS, Material.YELLOW_CONCRETE);
    }

    public static final class LastMove {

        private final Participant participant;
        private final Move move;

        public LastMove(Participant participant, Move move) {
            this.participant = participant;
            this.move = move;
        }

        public Participant getParticipant() {
            return participant;
        }

        public Move getMove() {
            return move;
        }
    }
}