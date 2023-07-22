package com.jagerbob.lapser.model;

import com.google.gson.Gson;
import com.jagerbob.lapser.helpers.BlockPosMapper;
import com.jagerbob.lapser.helpers.BlockStateMapper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class LapserFile {
    public BlockPos getRelativeCoordinatesA() {
        return relativeCoordinatesA;
    }

    public BlockPos getRelativeCoordinatesB() {
        return relativeCoordinatesB;
    }

    public List<String> getScan() {
        return scan;
    }

    private final BlockPos relativeCoordinatesA;
    private final BlockPos relativeCoordinatesB;
    private final List<String> scan;

    public LapserFile(BlockPos relativeCoordinatesA, BlockPos relativeCoordinatesB, List<String> scan)
    {
        this.relativeCoordinatesA = relativeCoordinatesA;
        this.relativeCoordinatesB = relativeCoordinatesB;
        this.scan = scan;
    }

    public static LapserFile fromJson(String tempFile) {
        return new Gson().fromJson(tempFile, LapserFile.class);
    }

    public PacketByteBuf toBuffer(BlockPos origin)
    {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(origin.add(relativeCoordinatesA));
        buf.writeBlockPos(origin.add(relativeCoordinatesB));
        scan.forEach(buf::writeString);
        return buf;
    }

    public static LapserFile fromBuffer(PacketByteBuf buf)
    {
        BlockPos coordinatesA = buf.readBlockPos();
        BlockPos coordinatesB = buf.readBlockPos();
        BlockPos origin = buf.readBlockPos();
        BlockPos relativeCoordinatesA = BlockPosMapper.toRelativeCoordinates(origin, coordinatesA);
        BlockPos relativeCoordinatesB = BlockPosMapper.toRelativeCoordinates(origin, coordinatesB);

        List<String> scan = new ArrayList<>();
        for(BlockPos ignored : BlockPos.iterate(coordinatesA, coordinatesB))
            scan.add(buf.readString());

        return new LapserFile(relativeCoordinatesA, relativeCoordinatesB, scan);
    }
}
