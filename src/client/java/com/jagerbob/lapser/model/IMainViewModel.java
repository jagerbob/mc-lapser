package com.jagerbob.lapser.model;

import net.minecraft.util.math.BlockPos;

public interface IMainViewModel {
    BlockPos getCoordinatesA();

    void setCoordinatesA(BlockPos coordinatesA);

    BlockPos getCoordinatesB();

    void setCoordinatesB(BlockPos coordinatesB);

    BlockPos getOrigin();

    void setOrigin(BlockPos origin);

    void setTempFile(String fileAsJson);

    String getTempFile();
}
