package com.jagerbob.lapser.helpers;

import net.minecraft.util.math.BlockPos;

public class StringHelper {
    public String toStringCoordinates(BlockPos coordinates) {
        return String.format("X: %d, Y: %d, Z: %d", coordinates.getX(), coordinates.getY(), coordinates.getZ());
    }
}
