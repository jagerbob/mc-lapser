package com.jagerbob.lapser.model;

import net.minecraft.util.math.BlockPos;

public class MainViewModel implements IMainViewModel {

    private BlockPos coordinatesA = new BlockPos(BlockPos.ORIGIN);
    private BlockPos coordinatesB = new BlockPos(BlockPos.ORIGIN);
    private BlockPos origin = new BlockPos(BlockPos.ORIGIN);
    private String tempFile;
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

    @Override
    public BlockPos getOrigin() {
        return origin;
    }

    @Override
    public void setOrigin(BlockPos origin) {
        this.origin = origin;
    }

    @Override
    public void setTempFile(String fileAsJson) {
        this.tempFile = fileAsJson;
    }

    @Override
    public String getTempFile() {
        return tempFile;
    }
}
