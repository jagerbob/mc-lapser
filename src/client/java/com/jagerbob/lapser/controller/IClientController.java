package com.jagerbob.lapser.controller;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public interface IClientController {
    void toggleEditor(MinecraftClient client);
    void play(MinecraftClient client);
    void setCoordinatesA();
    void setCoordinatesB();
    void saveArea();

    void retrieveArea(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender);
}
