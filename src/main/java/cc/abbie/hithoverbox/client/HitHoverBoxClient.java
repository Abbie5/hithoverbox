package cc.abbie.hithoverbox.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class HitHoverBoxClient implements ClientModInitializer {
    public static boolean enabled = true;
    private static final KeyMapping keyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.hithoverbox.toggle",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "category.hithoverbox"
    ));

    private static void toggle(Minecraft client) {
        enabled = !enabled;
        client.player.sendSystemMessage(Component.translatable(
                enabled ? "debug.hithoverbox.on" : "debug.hithoverbox.off"
        ));
    }

    @Override
    public void onInitializeClient() {
        // register key binding
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.consumeClick()) {
                toggle(client);
            }
        });

        // register command
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("hithoverbox").executes(context -> {
                toggle(Minecraft.getInstance());
                return 1;
            }));
        });

    }
}
