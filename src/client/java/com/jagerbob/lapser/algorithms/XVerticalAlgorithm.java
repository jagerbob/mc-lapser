package com.jagerbob.lapser.algorithms;

import net.minecraft.util.math.BlockPos;

public class XVerticalAlgorithm extends TimeLapseAlgorithm {

    @Override
    public void executeAlgorithm(BlockPos origin, BlockPos relativeCoordFromScanOrigin, String[][][] scan) throws InterruptedException {
        for(int x = 0; x < scan.length; x++)
            for(int y = 0; y < scan[x].length; y++)
                for(int z = 0; z < scan[x][y].length; z++)
                    this.placeBlock(origin.add(relativeCoordFromScanOrigin.add(new BlockPos(x, y, z))), scan[x][y][z]);
    }
}
