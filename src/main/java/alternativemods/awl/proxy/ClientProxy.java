package alternativemods.awl.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 19:06
 */
public class ClientProxy extends CommonProxy {

    public void addClientChat(String text) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
    }

}