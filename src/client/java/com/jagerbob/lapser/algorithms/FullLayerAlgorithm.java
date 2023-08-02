package com.jagerbob.lapser.algorithms;
import net.minecraft.util.math.BlockPos;

public class FullLayerAlgorithm extends TimeLapseAlgorithm {

    @Override
    public void executeAlgorithm(BlockPos origin, BlockPos relativeCoordFromScanOrigin, String[][][] scan) throws InterruptedException {
        for(int y = 0; y < scan[0].length; y++)
            for(int z = 0; z < scan[0][0].length; z++)
                for(int x = 0; x < scan.length; x++)
                    this.placeBlock(origin.add(relativeCoordFromScanOrigin.add(new BlockPos(x, y, z))), scan[x][y][z]);
    }
}
