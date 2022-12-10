package com.eintosti.chess.schematic;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class SavedBlock {

    private final BlockData blockData;
    private final int relativeX;
    private final int relativeY;
    private final int relativeZ;

    public SavedBlock(Location baseLocation, Block block) {
        this.blockData = block.getBlockData();
        Location relativeLocation = block.getLocation().subtract(baseLocation);
        this.relativeX = relativeLocation.getBlockX();
        this.relativeY = relativeLocation.getBlockY();
        this.relativeZ = relativeLocation.getBlockZ();
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public int getRelativeX() {
        return this.relativeX;
    }

    public int getRelativeY() {
        return this.relativeY;
    }

    public int getRelativeZ() {
        return this.relativeZ;
    }
}