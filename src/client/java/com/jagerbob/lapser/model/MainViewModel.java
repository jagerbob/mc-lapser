package com.jagerbob.lapser.model;

import net.minecraft.util.math.BlockPos;

public class MainViewModel implements IMainViewModel {

    private BlockPos coordinatesA = new BlockPos(BlockPos.ORIGIN);
    private BlockPos coordinatesB = new BlockPos(BlockPos.ORIGIN);

    public BlockPos getCoordinatesA() {
        return coordinatesA;
    }

    public void setCoordinatesA(BlockPos coordinatesA) {
        this.coordinatesA = coordinatesA;
    }

    public BlockPos getCoordinatesB() {
        return coordinatesB;
    }

    public void setCoordinatesB(BlockPos coordinatesB) {
        this.coordinatesB = coordinatesB;
    }
}
