package com.jagerbob.lapser.helpers;

import com.jagerbob.lapser.commands.IClientSideCommand;
import com.jagerbob.lapser.commands.PlayCommand;
import com.jagerbob.lapser.controller.IClientController;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class CommandManager {

    private final IClientSideCommand[] commands;

    public CommandManager(IClientController controller) {
        this.commands = new IClientSideCommand[] { new PlayCommand(controller) };
    }

    public void RegisterCommands() {
        for (IClientSideCommand command: commands)
            ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(command.build(dispatcher)));
    }
}
