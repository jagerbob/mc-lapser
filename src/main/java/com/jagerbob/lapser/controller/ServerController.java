package com.jagerbob.lapser.controller;

import com.jagerbob.lapser.config.Packets;
import com.jagerbob.lapser.helpers.BlockStateMapper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;

public class ServerController implements IServerController {

    private final Logger logger;

    public ServerController(Logger logger){
        this.logger = logger;
    }

    @Override
    public void saveArea(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        logger.debug(String.format("Received SAVE_AREA packet by %s", player.getIp()));
        BlockPos coordinatesA = buf.readBlockPos();
        BlockPos coordinatesB = buf.readBlockPos();

        server.execute(() -> {
            PacketByteBuf responseBuf = PacketByteBufs.create();
            for(BlockPos pos: BlockPos.iterate(coordinatesA, coordinatesB)) {
                responseBuf.writeString(BlockStateMapper.toString(player.getServerWorld().getBlockState(pos)));
            }
            ServerPlayNetworking.send(player, Packets.RETRIEVE_AREA_PACKET, responseBuf);
        });
    }

    @Override
    public void place(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        BlockPos pos = buf.readBlockPos();
        BlockState state = BlockStateMapper.toModel(buf.readString());
        state.getSoundGroup().getPlaceSound();
        server.execute(() -> {
            player.getServerWorld().setBlockState(pos, state);
            player.getServerWorld().playSound(null, pos, state.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 2.0f, 1.0f);
        });
    }
}
