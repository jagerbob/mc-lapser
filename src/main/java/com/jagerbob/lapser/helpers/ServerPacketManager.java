package com.jagerbob.lapser.helpers;

import com.jagerbob.lapser.config.Packets;
import com.jagerbob.lapser.controller.IServerController;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import static com.jagerbob.lapser.config.Packets.SAVE_AREA_PACKET;

public class ServerPacketManager {

    private final IServerController controller;

    public ServerPacketManager(IServerController controller) {
        this.controller = controller;
    }

    public void RegisterPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SAVE_AREA_PACKET, controller::saveArea);
        ServerPlayNetworking.registerGlobalReceiver(Packets.PLACE, controller::place);
    }
}
