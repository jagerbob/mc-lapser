package com.jagerbob.lapser.model;

import com.jagerbob.lapser.helpers.BlockPosMapper;
import net.minecraft.util.math.BlockPos;

public class MainViewModel implements IMainViewModel {

    private BlockPos coordinatesA = new BlockPos(BlockPos.ORIGIN);
    private BlockPos coordinatesB = new BlockPos(BlockPos.ORIGIN);
    private BlockPos origin = new BlockPos(BlockPos.ORIGIN);
    private String[][][] scan;
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
    public void setScan(String[][][] scan) {
        this.scan = scan;
    }

    @Override
    public String[][][] getScan() {
        return scan;
    }

    @Override
    public BlockPos getRelativeCoordinatesA() {
        return BlockPosMapper.toRelativeCoordinates(this.origin, this.coordinatesA);
    }

    @Override
    public BlockPos getRelativeCoordinatesB() {
        return BlockPosMapper.toRelativeCoordinates(this.origin, this.coordinatesB);
    }
}
