package com.jagerbob.lapser.algorithms;

import com.jagerbob.lapser.helpers.BlockPosMapper;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public abstract class TimeLapseAlgorithm {

    public void play(BlockPos playerPos, BlockPos origin, BlockPos scanOrigin, String[][][] scan) {
        new Thread(() -> {
            try {
                this.executeAlgorithm(playerPos, BlockPosMapper.toRelativeCoordinates(origin, scanOrigin), scan);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public abstract void executeAlgorithm(BlockPos origin, BlockPos relativeDistanceFromScanOrigin, String[][][] scan) throws InterruptedException;
}
