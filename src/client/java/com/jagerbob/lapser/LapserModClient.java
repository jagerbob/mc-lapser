package com.jagerbob.lapser;

import com.jagerbob.lapser.commands.CommandHandler;
import com.jagerbob.lapser.controller.IController;
import com.jagerbob.lapser.controller.MainController;
import com.jagerbob.lapser.helpers.KeyBindManager;
import net.fabricmc.api.ClientModInitializer;

public class LapserModClient implements ClientModInitializer {

	private IController controller;
	private KeyBindManager keyBindManager;

	public LapserModClient()
	{
		this.controller = new MainController(new CommandHandler());
		this.keyBindManager = new KeyBindManager(this.controller);
	}

	@Override
	public void onInitializeClient() {
		keyBindManager.RegisterBindings();
	}
}