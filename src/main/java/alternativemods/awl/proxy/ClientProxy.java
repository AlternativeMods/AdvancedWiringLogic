package alternativemods.awl.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 19:06
 */
public class ClientProxy extends CommonProxy {

    public static KeyBinding keyUp = new KeyBinding("Up", Keyboard.KEY_UP, "Advanced Wiring Logic");
    public static KeyBinding keyDown = new KeyBinding("Down", Keyboard.KEY_DOWN, "Advanced Wiring Logic");
    public static KeyBinding keyLeft = new KeyBinding("Left", Keyboard.KEY_LEFT, "Advanced Wiring Logic");
    public static KeyBinding keyRight = new KeyBinding("Right", Keyboard.KEY_RIGHT, "Advanced Wiring Logic");

    @Override
    public void addClientChat(String text) {
        super.addClientChat(text);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
    }

    @Override
    public void registerKeyBindings() {
        super.registerKeyBindings();

        ClientRegistry.registerKeyBinding(keyUp);
        ClientRegistry.registerKeyBinding(keyDown);
        ClientRegistry.registerKeyBinding(keyLeft);
        ClientRegistry.registerKeyBinding(keyRight);
    }
}
