package com.jagerbob.lapser.algorithms;

import com.jagerbob.lapser.config.Packets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class ZVerticalAlgorithm extends TimeLapseAlgorithm {

    @Override
    public void executeAlgorithm(BlockPos origin, BlockPos relativeCoordFromScanOrigin, String[][][] scan) throws InterruptedException {
        for(int z = 0; z < scan[0][0].length; z++)
            for(int y = 0; y < scan[0].length; y++)
                for(int x = 0; x < scan.length; x++)
                    this.placeBlock(origin.add(relativeCoordFromScanOrigin.add(new BlockPos(x, y, z))), scan[x][y][z]);
    }
}
