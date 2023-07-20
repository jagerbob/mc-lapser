package com.jagerbob.lapser.controller;

import com.jagerbob.lapser.config.Packets;
import com.mojang.authlib.properties.PropertyMap;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import net.minecraft.registry.Registry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Objects;

public class ServerController implements IServerController {

    private final Logger logger;

    public ServerController(Logger logger){
        this.logger = logger;
    }

    @Override
    public void saveArea(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        logger.debug(String.format("Received SAVE_AREA packet by %s", player.getIp()));
        BlockPos posA = buf.readBlockPos();

        server.execute(() -> {
            BlockState blockState = player.getServerWorld().getBlockState(posA);
            PacketByteBuf responseBuf = PacketByteBufs.create();
            //responseBuf.wri
            String blockStateAsString = from(blockState);
            ServerPlayNetworking.send(player, Packets.RETRIEVE_AREA_PACKET, responseBuf);

            //BlockPos newPos = posA.add(1, 1, 1);
            //BlockState newBlockState = new BlockState(Registries.BLOCK.get(new Identifier("minecraft:spruce_stairs"), )));
            //layer.getServerWorld().setBlockState(newPos, blockState);
        });
    }

    private String from(BlockState state) {
        StringBuilder builder = new StringBuilder();
        builder.append(Objects.requireNonNull(Registries.BLOCK.getId(state.getBlock())));
        boolean flag = true;
        Iterator<Property<?>> iterator = state.getProperties().iterator();
        while (iterator.hasNext()) {
            if (flag) {
                builder.append("[");
                flag = false;
            }

            Property<?> property = iterator.next();
            builder.append(property.getName());
            builder.append("=");

            if (state.get(property) instanceof Enum<?>) {
                // Enum might have override toString
                builder.append(((StringIdentifiable) state.get(property)).asString());
            } else {
                builder.append(state.get(property).toString());
            }

            if (iterator.hasNext()) {
                builder.append(",");
            }
        }
        if (!flag) {
            builder.append("]");
        }
        return builder.toString();
    }
}
