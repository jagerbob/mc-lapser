package com.jagerbob.lapser.helpers;

import net.minecraft.util.math.BlockPos;

public class BlockPosMapper {

    public static BlockPos toRelativeCoordinates(BlockPos origin, BlockPos target) {
        int relativeX = target.getX() - origin.getX();
        int relativeY = target.getY() - origin.getY();
        int relativeZ = target.getZ() - origin.getZ();

        return new BlockPos(relativeX, relativeY, relativeZ);
    }
}
