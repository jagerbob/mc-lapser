package com.jagerbob.lapser.helpers;

import com.jagerbob.lapser.controller.IClientController;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class EventManager {

    private final IClientController controller;

    public EventManager(IClientController controller)
    {
        this.controller = controller;
    }

    public void RegisterEventHandlers()
    {
        PlayerBlockBreakEvents.BEFORE.register(((world, player, pos, state, blockEntity) -> {
            if(player.getMainHandStack().getItem() == Items.BLAZE_ROD) {
                this.controller.setCoordinatesA(pos, player);
                return false;
            }
            return true;
        }));

        UseBlockCallback.EVENT.register((player, world, hand, result) -> {
            if (player.getStackInHand(hand).getItem() == Items.BLAZE_ROD) {
                BlockPos pos = result.getBlockPos();
                this.controller.setCoordinatesB(player.getBlockPos(), player);
                return ActionResult.CONSUME;
            }
            return ActionResult.PASS;
        });
    }
}
