package com.jagerbob.lapser.commands;

import com.jagerbob.lapser.controller.IClientController;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.Objects;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class PlayCommand implements IClientSideCommand {

    private final IClientController controller;

    public PlayCommand(IClientController controller)
    {
        this.controller = controller;
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        return ClientCommandManager.literal("lapser")
                .then(literal("play")
                        .then(argument("algorithm", word())
                                .suggests(new AlgorithmSuggestionProvider())
                                    .then(argument("speed", IntegerArgumentType.integer(0, 100))
                                            .executes((ctx) -> this.execute(ctx, IntegerArgumentType.getInteger(ctx, "speed"))))
                                .executes((ctx) -> this.execute(ctx, 80))));
    }

    private int execute(CommandContext<FabricClientCommandSource> context, int speed) {
        this.controller.play(Objects.requireNonNull(context.getSource().getPlayer()).getBlockPos(), getString(context, "algorithm"), speed);
        return Command.SINGLE_SUCCESS;
    }
}
