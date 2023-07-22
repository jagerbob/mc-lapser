package com.jagerbob.lapser.helpers;

import com.jagerbob.lapser.config.Packets;
import com.jagerbob.lapser.controller.IClientController;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientPacketManager {

    private final IClientController controller;

    public ClientPacketManager(IClientController controller) {
        this.controller = controller;
    }

    public void RegisterPackets() {
        ClientPlayNetworking.registerGlobalReceiver(Packets.RETRIEVE_AREA_PACKET, controller::retrieveArea);
    }
}
