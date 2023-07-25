package com.jagerbob.lapser.controller;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public interface IClientController {
    void toggleEditor(MinecraftClient client);
    void play(BlockPos origin, String algorithm, int speed);
    void setCoordinatesA(BlockPos pos, PlayerEntity player);
    void setCoordinatesB(BlockPos pos, PlayerEntity player);
    void saveArea(BlockPos origin, PlayerEntity player);

    void retrieveArea(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender);
}
