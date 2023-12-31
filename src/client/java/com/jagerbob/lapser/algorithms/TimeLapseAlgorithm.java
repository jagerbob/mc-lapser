package com.jagerbob.lapser.algorithms;

import com.jagerbob.lapser.config.Packets;
import com.jagerbob.lapser.helpers.BlockPosMapper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public abstract class TimeLapseAlgorithm {

    protected boolean excludeAir = true;
    protected int placeDelayMs = 5;

    public void configure(boolean excludeAir, int placeDelayMs)
    {
        this.excludeAir = excludeAir;
        this.placeDelayMs = placeDelayMs;
    }

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

    public void placeBlock(BlockPos pos, String blockStateAsString) throws InterruptedException {
        if(this.excludeAir && Objects.equals(blockStateAsString, "minecraft:air"))
            return;
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeString(blockStateAsString);
        ClientPlayNetworking.send(Packets.PLACE, buf);
        Thread.sleep(this.placeDelayMs);
    }
}
