package com.jagerbob.lapser.algorithms;

import com.jagerbob.lapser.config.Packets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class FullLayerAlgorithm extends TimeLapseAlgorithm {

    @Override
    public void executeAlgorithm(BlockPos origin, BlockPos relativeCoordFromScanOrigin, String[][][] scan) throws InterruptedException {
        for(int y = 0; y < scan[0].length; y++)
            for(int z = 0; z < scan[0][0].length; z++)
                for(int x = 0; x < scan.length; x++) {
                    if(this.excludeAir && Objects.equals(scan[x][y][z], "minecraft:air"))
                        continue;
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBlockPos(origin.add(relativeCoordFromScanOrigin.add(new BlockPos(x, y, z))));
                    buf.writeString(scan[x][y][z]);
                    ClientPlayNetworking.send(Packets.PLACE, buf);
                    Thread.sleep(this.placeDelayMs);
                }
    }
}
