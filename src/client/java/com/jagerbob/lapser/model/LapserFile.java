package com.jagerbob.lapser.model;

import com.google.gson.Gson;
import com.jagerbob.lapser.helpers.BlockPosMapper;
import com.jagerbob.lapser.helpers.BlockStateMapper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LapserFile {

    private final BlockPos relativeCoordinatesA;
    private final BlockPos relativeCoordinatesB;
    private final Map<BlockPos, String> scan;

    public LapserFile(BlockPos relativeCoordinatesA, BlockPos relativeCoordinatesB, Map<BlockPos, String> scan)
    {
        this.relativeCoordinatesA = relativeCoordinatesA;
        this.relativeCoordinatesB = relativeCoordinatesB;
        this.scan = scan;
    }

    public BlockPos getRelativeCoordinatesA() {
        return relativeCoordinatesA;
    }

    public BlockPos getRelativeCoordinatesB() {
        return relativeCoordinatesB;
    }

    public Map<BlockPos, String> getScan() {
        return scan;
    }

    public static LapserFile fromJson(String tempFile) {
        return new Gson().fromJson(tempFile, LapserFile.class);
    }

    public static LapserFile fromBuffer(PacketByteBuf buf)
    {
        BlockPos coordinatesA = buf.readBlockPos();
        BlockPos coordinatesB = buf.readBlockPos();
        BlockPos origin = buf.readBlockPos();
        BlockPos relativeCoordinatesA = BlockPosMapper.toRelativeCoordinates(origin, coordinatesA);
        BlockPos relativeCoordinatesB = BlockPosMapper.toRelativeCoordinates(origin, coordinatesB);

        Map<BlockPos, String> scan = new HashMap<>();
        for(BlockPos ignored : BlockPos.iterate(coordinatesA, coordinatesB))
            scan.put(buf.readBlockPos(), buf.readString());

        return new LapserFile(relativeCoordinatesA, relativeCoordinatesB, scan);
    }
}
