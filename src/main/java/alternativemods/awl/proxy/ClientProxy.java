package alternativemods.awl.proxy;

import alternativemods.awl.Main;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 19:06
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void addClientChat(String text) {
        super.addClientChat(text);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
    }

    @Override
    public void registerKeyBindings() {
        super.registerKeyBindings();

        ClientRegistry.registerKeyBinding(Main.keyUp);
        ClientRegistry.registerKeyBinding(Main.keyDown);
        ClientRegistry.registerKeyBinding(Main.keyLeft);
        ClientRegistry.registerKeyBinding(Main.keyRight);
    }

}