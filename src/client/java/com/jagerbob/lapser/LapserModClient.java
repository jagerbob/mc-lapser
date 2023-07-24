package com.jagerbob.lapser;

import com.jagerbob.lapser.controller.ClientController;
import com.jagerbob.lapser.controller.IClientController;
import com.jagerbob.lapser.helpers.ClientPacketManager;
import com.jagerbob.lapser.helpers.CommandManager;
import com.jagerbob.lapser.helpers.EventManager;
import com.jagerbob.lapser.helpers.KeyBindManager;
import net.fabricmc.api.ClientModInitializer;

public class LapserModClient implements ClientModInitializer {
	private final IClientController controller;
	private final KeyBindManager keyBindManager;
	private final ClientPacketManager packetManager;
	private final CommandManager commandManager;
	private final EventManager eventManager;

	public LapserModClient()
	{
		this.controller = new ClientController();
		this.keyBindManager = new KeyBindManager(this.controller);
		this.packetManager = new ClientPacketManager(this.controller);
		this.commandManager = new CommandManager(this.controller);
		this.eventManager = new EventManager(this.controller);
	}

	@Override
	public void onInitializeClient() {
		keyBindManager.RegisterBindings();
		packetManager.RegisterPackets();
		commandManager.RegisterCommands();
		eventManager.RegisterEventHandlers();
	}
}