package com.jagerbob.lapser.helpers;

import com.jagerbob.lapser.actions.editor.ToggleEditorAction;
import com.jagerbob.lapser.commands.CommandDispatcher;
import com.jagerbob.lapser.model.KeyBind;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeyBindHelper {

    private CommandDispatcher commandDispatcher;
    private final KeyBind[] keyBinds = new KeyBind[] {
            new KeyBind("com.jagerbob.lapser.toggleZoneEditor", GLFW.GLFW_KEY_R, "general.lapser", new ToggleEditorAction())
    };

    public KeyBindHelper(CommandDispatcher commandDispatcher)
    {
        this.commandDispatcher = commandDispatcher;
    }
    
    public void RegisterBindings() {
        for (KeyBind keyBind: keyBinds)
            KeyBindingHelper.registerKeyBinding(keyBind);
    }


}
