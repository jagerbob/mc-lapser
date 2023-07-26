package com.jagerbob.lapser.commands;

import com.jagerbob.lapser.controller.IClientController;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.Objects;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class SaveCommand implements IClientSideCommand {

    private final IClientController controller;

    public SaveCommand(IClientController controller)
    {
        this.controller = controller;
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        return ClientCommandManager.literal("lapser")
                .then(literal("save")
                        .executes(this::execute));
    }

    private int execute(CommandContext<FabricClientCommandSource> context) {
        this.controller.saveArea(Objects.requireNonNull(context.getSource().getPlayer()).getBlockPos(), context.getSource().getPlayer());
        return Command.SINGLE_SUCCESS;
    }
}
