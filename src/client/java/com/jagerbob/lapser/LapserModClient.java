package com.jagerbob.lapser;

import com.jagerbob.lapser.controller.IClientController;
import com.jagerbob.lapser.controller.ClientController;
import com.jagerbob.lapser.helpers.ClientPacketManager;
import com.jagerbob.lapser.helpers.KeyBindManager;
import net.fabricmc.api.ClientModInitializer;

public class LapserModClient implements ClientModInitializer {

	private final ClientPacketManager packetManager;
	private IClientController controller;
	private KeyBindManager keyBindManager;

	public LapserModClient()
	{
		this.controller = new ClientController();
		this.keyBindManager = new KeyBindManager(this.controller);
		this.packetManager = new ClientPacketManager(this.controller);
	}

	@Override
	public void onInitializeClient() {
		keyBindManager.RegisterBindings();
		packetManager.RegisterPackets();
	}
}