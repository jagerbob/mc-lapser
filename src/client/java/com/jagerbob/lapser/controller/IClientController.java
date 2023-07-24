package com.jagerbob.lapser.controller;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public interface IClientController {
    void toggleEditor(MinecraftClient client);
    void play(BlockPos origin, String algorithm, int speed);
    void setCoordinatesA(BlockPos pos);
    void setCoordinatesB(BlockPos pos);
    void saveArea(BlockPos origin);

    void retrieveArea(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender);
}
