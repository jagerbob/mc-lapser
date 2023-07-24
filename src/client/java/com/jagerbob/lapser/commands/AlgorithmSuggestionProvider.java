package com.jagerbob.lapser.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.concurrent.CompletableFuture;

class AlgorithmSuggestionProvider implements SuggestionProvider<FabricClientCommandSource> {

    private static final String[] algorithms = new String[] {
            "x_vertical",
            "z_vertical",
            "full_layer",
            "square_layer"
    };

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
        for(String alg : algorithms)
            builder.suggest(alg);
        return builder.buildFuture();
    }
}
