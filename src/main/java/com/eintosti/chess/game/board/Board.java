package com.eintosti.chess.game.board;

import com.eintosti.chess.game.board.Move.Outcome;
import com.eintosti.chess.game.board.Tile.Name;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Color;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.game.piece.pieces.Bishop;
import com.eintosti.chess.game.piece.pieces.King;
import com.eintosti.chess.game.piece.pieces.Knight;
import com.eintosti.chess.game.piece.pieces.Pawn;
import com.eintosti.chess.game.piece.pieces.Queen;
import com.eintosti.chess.game.piece.pieces.Rook;
import com.eintosti.chess.schematic.Schematic;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int MAX_TILES = 8;

    private static final int WHITE_MAIN = 0;
    private static final int WHITE_PAWNS = 1;
    private static final int BLACK_MAIN = 7;
    private static final int BLACK_PAWNS = 6;

    private final Participant white, black;
    private final Tile[][] tiles;

    private final Schematic schematic;
    private final Orientation orientation;
    private final Location pos1, pos2;
    private final int tileWidthX, tileWidthZ;

    public Board(Participant white, Participant black, Location pos1, Location pos2) {
        this.white = white;
        this.black = black;

        this.schematic = new Schematic()
                .setPos1(pos1)
                .setPos2(pos2)
                .setBaseLocation(pos1)
                .saveCurrentSelection();

        this.pos1 = pos1;
        this.pos2 = pos2;

        final int startX = pos1.getBlockX();
        final int startZ = pos1.getBlockZ();
        final int endX = pos2.getBlockX();
        final int endZ = pos2.getBlockZ();
        this.tileWidthX = (Math.abs(endX - startX) + 1) / MAX_TILES;
        this.tileWidthZ = (Math.abs(endZ - startZ) + 1) / MAX_TILES;
        this.orientation = Orientation.parseOrientation(endX - startX, endZ - startZ);

        this.tiles = new Tile[MAX_TILES][MAX_TILES];

        // White
        tiles[Name.A.getNum()][WHITE_MAIN] = new Tile(Name.A.getNum(), WHITE_MAIN, new Rook(white));
        tiles[Name.B.getNum()][WHITE_MAIN] = new Tile(Name.B.getNum(), WHITE_MAIN, new Knight(white));
        tiles[Name.C.getNum()][WHITE_MAIN] = new Tile(Name.C.getNum(), WHITE_MAIN, new Bishop(white));
        tiles[Name.D.getNum()][WHITE_MAIN] = new Tile(Name.D.getNum(), WHITE_MAIN, new Queen(white));
        tiles[Name.E.getNum()][WHITE_MAIN] = new Tile(Name.E.getNum(), WHITE_MAIN, new King(white));
        tiles[Name.F.getNum()][WHITE_MAIN] = new Tile(Name.F.getNum(), WHITE_MAIN, new Bishop(white));
        tiles[Name.G.getNum()][WHITE_MAIN] = new Tile(Name.G.getNum(), WHITE_MAIN, new Knight(white));
        tiles[Name.H.getNum()][WHITE_MAIN] = new Tile(Name.H.getNum(), WHITE_MAIN, new Rook(white));

        for (int x = 0; x < MAX_TILES; x++) {
            tiles[x][WHITE_PAWNS] = new Tile(x, WHITE_PAWNS, new Pawn(white));
        }

        // Empty
        for (int x = 0; x < MAX_TILES; x++) {
            for (int z = 2; z < BLACK_PAWNS; z++) {
                tiles[x][z] = new Tile(x, z);
            }
        }

        // Black
        tiles[Name.A.getNum()][BLACK_MAIN] = new Tile(Name.A.getNum(), BLACK_MAIN, new Rook(black));
        tiles[Name.B.getNum()][BLACK_MAIN] = new Tile(Name.B.getNum(), BLACK_MAIN, new Knight(black));
        tiles[Name.C.getNum()][BLACK_MAIN] = new Tile(Name.C.getNum(), BLACK_MAIN, new Bishop(black));
        tiles[Name.D.getNum()][BLACK_MAIN] = new Tile(Name.D.getNum(), BLACK_MAIN, new Queen(black));
        tiles[Name.E.getNum()][BLACK_MAIN] = new Tile(Name.E.getNum(), BLACK_MAIN, new King(black));
        tiles[Name.F.getNum()][BLACK_MAIN] = new Tile(Name.F.getNum(), BLACK_MAIN, new Bishop(black));
        tiles[Name.G.getNum()][BLACK_MAIN] = new Tile(Name.G.getNum(), BLACK_MAIN, new Knight(black));
        tiles[Name.H.getNum()][BLACK_MAIN] = new Tile(Name.H.getNum(), BLACK_MAIN, new Rook(black));

        for (int x = 0; x < MAX_TILES; x++) {
            tiles[x][BLACK_PAWNS] = new Tile(x, BLACK_PAWNS, new Pawn(black));
        }
    }

    /**
     * Gets the tile from local coordinates.
     *
     * @param x The local x coordinate (from 0-7)
     * @param z The local z coordinate (from 0-7)
     * @return The tile located at the specified coordinates
     */
    public Tile getTile(int x, int z) {
        return tiles[x][z];
    }

    /**
     * Gets the tile which is located at a given format (e.g. C6).
     *
     * @param name The letter of the row
     * @param z    The local z coordinate (from 1-8)
     * @return The tile located at the specified coordinates
     */
    public Tile getTile(Name name, int z) {
        return tiles[name.getNum()][z - 1];
    }

    /**
     * Gets the tile at a location.
     *
     * @param location The block to check
     * @return The tile at the location, if any
     */
    @Nullable
    public Tile getTile(Location location) {
        int normalizedBlockX;
        int normalizedBlockZ;

        switch (orientation) {
            case WEST_NORTH, EAST_SOUTH -> {
                normalizedBlockX = (location.getBlockZ() - pos1.getBlockZ()) * orientation.getZ();
                normalizedBlockZ = (location.getBlockX() - pos1.getBlockX()) * orientation.getX();
            }
            default -> {
                normalizedBlockX = (location.getBlockX() - pos1.getBlockX()) * orientation.getX();
                normalizedBlockZ = (location.getBlockZ() - pos1.getBlockZ()) * orientation.getZ();
            }
        }

        int normalizedBoardX = Math.floorDiv(normalizedBlockX, tileWidthX);
        int normalizedBoardZ = Math.floorDiv(normalizedBlockZ, tileWidthZ);

        if (normalizedBoardX >= 0 && normalizedBoardX <= 7 && normalizedBoardZ >= 0 && normalizedBoardZ <= 7) {
            return tiles[normalizedBoardX][normalizedBoardZ];
        }

        return null;
    }

    /**
     * Gets the corner locations of a {@link Tile}.
     *
     * @param tile The tile
     * @return An array of two locations: The first is the lower location, the second is the higher
     */
    public Location[] getTileCorners(Tile tile) {
        int addX = tile.getX() * tileWidthX;
        int addZ = tile.getZ() * tileWidthZ;

        return new Location[]{
                pos1.clone().add(addX * orientation.getX(), 0, addZ * orientation.getZ()),
                pos1.clone().add((tileWidthX + addX - 1) * orientation.getX(), 0, (tileWidthZ + addZ - 1) * orientation.getZ())
        };
    }

    /**
     * Gets the floor of a tile.
     *
     * @param tile The tile
     * @return A list of all blocks which make up the tile's floor
     */
    public List<Block> getTileFloor(Tile tile) {
        Location[] corners = getTileCorners(tile);
        Location pos1 = corners[0];
        Location pos2 = corners[1];
        World world = pos1.getWorld();

        int x1 = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int z1 = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int x2 = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int z2 = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        List<Block> blocks = new ArrayList<>();
        for (int x = x1; x <= x2; x++) {
            for (int z = z1; z <= z2; z++) {
                blocks.add(world.getBlockAt(x, pos1.getBlockY(), z));
            }
        }
        return blocks;
    }

    /**
     * Checks if a move is valid.
     *
     * @param tileToMoveTo The tile the piece wants to move to
     * @param selectedTile The currently selected tile
     * @param participant  The participant making the move
     * @return {@code true} if the move is valid, otherwise {@code false}
     */
    public boolean canMove(Tile tileToMoveTo, Tile selectedTile, Participant participant) {
        if (selectedTile == null) {
            return false;
        }

        Piece selectedPiece = selectedTile.getPiece();
        if (!selectedPiece.isValidMove(this, selectedTile.getX(), selectedTile.getZ(), tileToMoveTo.getX(), tileToMoveTo.getZ())) {
            return false;
        }

        Piece pieceOnTileToMoveTo = tileToMoveTo.getPiece();
        if (pieceOnTileToMoveTo == null) {
            return true;
        }

        return participant != null && pieceOnTileToMoveTo.getColor() != participant.getColor();
    }

    /**
     * Make a {@link Piece} move on the board.
     *
     * @param move  The move the piece should make
     * @param color The color of the piece
     * @return The outcome of the move
     */
    public Outcome makeMove(Move move, Color color) {
        int startX = move.getStartX(), startZ = move.getStartZ();
        int endX = move.getEndX(), endZ = move.getEndZ();
        boolean isTake = getTile(endX, endZ).isOccupied();

        Tile oldTile = getTile(startX, startZ);
        tiles[endX][endZ] = new Tile(endX, endZ, oldTile.getPiece());
        tiles[startX][startZ] = new Tile(startX, startZ);

        if (move.isCastling()) {
            //TODO: Implement correctly
            if (endX == 6 && endZ == Name.A.getNum()) {
                tiles[Name.F.getNum()][0] = tiles[Name.H.getNum()][0];
                tiles[Name.H.getNum()][0] = new Tile(Name.H.getNum(), 0);
            }
            if (endX == 2 && endZ == Name.A.getNum()) {
                tiles[Name.D.getNum()][0] = tiles[Name.A.getNum()][0];
                tiles[Name.A.getNum()][0] = new Tile(Name.A.getNum(), 0);
            }
            if (endX == 6 && endZ == Name.H.getNum()) {
                tiles[Name.F.getNum()][7] = tiles[Name.H.getNum()][7];
                tiles[Name.H.getNum()][7] = new Tile(Name.H.getNum(), 7);
            }
            if (endX == 2 && endZ == Name.H.getNum()) {
                tiles[Name.D.getNum()][7] = tiles[Name.A.getNum()][7];
                tiles[Name.A.getNum()][7] = new Tile(Name.A.getNum(), 7);
            }
            return Outcome.CASTLE;
        }

        if (oldTile.getPiece() instanceof Pawn) {
            if (color == Color.WHITE && endZ == BLACK_MAIN) {
                tiles[endX][endZ] = new Tile(endX, endZ, new Queen(white));
                return Outcome.PROMOTION;
            } else if (color == Color.BLACK && endZ == WHITE_MAIN) {
                tiles[endX][endZ] = new Tile(endX, endZ, new Queen(black));
                return Outcome.PROMOTION;
            }
        }

        return isTake ? Outcome.TAKE : Outcome.MOVE;
    }

    public Schematic getSchematic() {
        return schematic;
    }

    public Location getBaseLocation() {
        return pos1;
    }

    public int getHeight() {
        return pos2.getBlockY() - pos1.getBlockY();
    }

    public Orientation getOrientation() {
        return orientation;
    }
}