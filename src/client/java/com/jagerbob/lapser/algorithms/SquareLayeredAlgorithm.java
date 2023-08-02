package com.jagerbob.lapser.algorithms;

import net.minecraft.util.math.BlockPos;

public class SquareLayeredAlgorithm extends TimeLapseAlgorithm {

    @Override
    public void executeAlgorithm(BlockPos origin, BlockPos relativeCoordFromScanOrigin, String[][][] scan) throws InterruptedException {
        for(int y = 0; y < scan[0].length; y++) {
            int x;
            int z;
            int startX = 0;
            int startZ = 0;
            int endX = scan.length - 1;
            int endZ = scan[0][0].length - 1;

            while (startX <= endX && startZ <= endZ) {
                for (x = startX; x <= endX; x++)
                    placeBlock(origin.add(relativeCoordFromScanOrigin.add(new BlockPos(x, y, startZ))), scan[x][y][startZ]);
                startZ++;

                for (z = startZ; z <= endZ; z++)
                    placeBlock(origin.add(relativeCoordFromScanOrigin.add(new BlockPos(endX, y, z))), scan[endX][y][z]);
                endX--;

                for (x = endX; x >= startX; x--)
                    placeBlock(origin.add(relativeCoordFromScanOrigin.add(new BlockPos(x, y, endZ))), scan[x][y][endZ]);
                endZ--;

                for (z = endZ; z >= startZ; z--)
                    placeBlock(origin.add(relativeCoordFromScanOrigin.add(new BlockPos(startX, y, z))), scan[startX][y][z]);
                startX++;
            }
        }
    }
}
