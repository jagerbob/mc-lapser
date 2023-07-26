package com.jagerbob.lapser.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public interface IClientSideCommand {

    LiteralArgumentBuilder<FabricClientCommandSource> build(CommandDispatcher<FabricClientCommandSource> dispatcher);
}
