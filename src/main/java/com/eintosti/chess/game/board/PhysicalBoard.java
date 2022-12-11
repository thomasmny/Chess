package com.eintosti.chess.game.board;

import com.eintosti.chess.schematic.Schematic;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PhysicalBoard extends Board {

    private final Schematic schematic;
    private final Orientation orientation;
    private final Location pos1, pos2;
    private final int tileWidthX, tileWidthZ;

    public PhysicalBoard(Location pos1, Location pos2) {
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