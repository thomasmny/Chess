package com.eintosti.chess.game.board;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

/**
 * The orientation of a {@link PhysicalBoard} is the direction in which the player faces when standing on the tile A1.
 * <p>
 * In total there are 4 different orientations which differ in their normalized coordinates.
 * <ul>
 *   <li>The <b>x-coordinate</b> is the direction in which a the tile numbers increase</li>
 *   <li>The <b>z-coordinate</b> is the direction in which a the tile letters increase</li>
 * </ul>
 * <p>
 * For example: If the tile numbers increase when looking in <b>negative</b> x-direction and the tile letters increase when
 * looking in <b>positive</b> z-direction, then the board orientation is {@link #SOUTH_WEST}.
 */
public enum Orientation {
    NORTH_EAST(1, -1),
    EAST_SOUTH(1, 1),
    WEST_NORTH(-1, -1),
    SOUTH_WEST(-1, 1);

    private final int x, z;

    Orientation(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Nullable
    public static Orientation parseOrientation(int x, int z) {
        int normalizedX = x / Math.abs(x);
        int normalizedZ = z / Math.abs(z);

        for (Orientation orientation : values()) {
            if (orientation.getX() == normalizedX & orientation.getZ() == normalizedZ) {
                Bukkit.broadcastMessage("Orientation: " + orientation);
                return orientation;
            }
        }

        return null;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public boolean isSameSign() {
        return x == z;
    }
}