package com.eintosti.chess.schematic;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Schematic {

    private Location pos1;
    private Location pos2;
    private List<SavedBlock> savedBlocks;
    private Location baseLocation;

    public Schematic() {
        this.savedBlocks = new ArrayList<>();
    }

    public Schematic setPos1(Location pos1) {
        this.pos1 = pos1;
        return this;
    }

    public Schematic setPos2(Location pos2) {
        this.pos2 = pos2;
        return this;
    }

    public Schematic setBaseLocation(Location baseLocation) {
        this.baseLocation = baseLocation;
        return this;
    }

    public Schematic saveCurrentSelection() {
        if (pos1 == null || pos2 == null) {
            throw new IllegalStateException("Selected position cannot be null");
        }

        World world = pos1.getWorld();
        int x1 = pos1.getBlockX();
        int y1 = pos1.getBlockY();
        int z1 = pos1.getBlockZ();
        int x2 = pos2.getBlockX();
        int y2 = pos2.getBlockY();
        int z2 = pos2.getBlockZ();

        int xMin = Math.min(x1, x2);
        int xMax = Math.max(x1, x2);
        int yMin = Math.min(y1, y2);
        int yMax = Math.max(y1, y2);
        int zMin = Math.min(z1, z2);
        int zMax = Math.max(z1, z2);

        List<SavedBlock> blocks = new ArrayList<>();

        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                for (int z = zMin; z <= zMax; z++) {
                    blocks.add(new SavedBlock(
                            baseLocation.clone(),
                            new Location(world, x, y, z).getBlock()
                    ));
                }
            }
        }

        this.savedBlocks = blocks;
        return this;
    }

    public void paste(Location location) {
        for (SavedBlock savedBlock : this.savedBlocks) {
            location.clone()
                    .add(savedBlock.getRelativeX(), savedBlock.getRelativeY(), savedBlock.getRelativeZ())
                    .getBlock()
                    .setBlockData(savedBlock.getBlockData(), false);
        }
    }
}