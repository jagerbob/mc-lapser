package com.jagerbob.lapser.algorithms;

import com.jagerbob.lapser.config.Packets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

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
                for (x = startX; x <= endX; x++) {
                    process(x, y, startZ, origin, relativeCoordFromScanOrigin, scan);
                }
                startZ++;

                for (z = startZ; z <= endZ; z++) {
                    process(endX, y, z, origin, relativeCoordFromScanOrigin, scan);
                }
                endX--;

                for (x = endX; x >= startX; x--) {
                    process(x, y, endZ, origin, relativeCoordFromScanOrigin, scan);
                }
                endZ--;

                for (z = endZ; z >= startZ; z--) {
                    process(startX, y, z, origin, relativeCoordFromScanOrigin, scan);
                }
                startX++;
            }
        }
    }

    private void process(int x, int y, int z, BlockPos origin, BlockPos relativeCoordFromScanOrigin, String[][][] scan) throws InterruptedException {
        if(this.excludeAir && Objects.equals(scan[x][y][z], "minecraft:air"))
            return;
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(origin.add(relativeCoordFromScanOrigin.add(new BlockPos(x, y, z))));
        buf.writeString(scan[x][y][z]);
        ClientPlayNetworking.send(Packets.PLACE, buf);
        Thread.sleep(this.placeDelayMs);
    }
}
