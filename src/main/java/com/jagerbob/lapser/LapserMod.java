package com.jagerbob.lapser;

import com.jagerbob.lapser.config.Config;
import com.jagerbob.lapser.controller.ServerController;
import com.jagerbob.lapser.helpers.ServerPacketManager;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LapserMod implements ModInitializer {

    private final Logger logger = LoggerFactory.getLogger(Config.MOD_ID);
    private final ServerController controller;
    private final ServerPacketManager packetManager;

    public LapserMod() {
        this.controller = new ServerController(logger);
        this.packetManager = new ServerPacketManager(controller);
    }

    @Override
    public void onInitialize() {
        packetManager.RegisterPackets();
    }
}
