package alternativemods.awl.client;

import alternativemods.awl.proxy.ClientProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;

/**
 * No description given
 *
 * @author jk-5
 */
@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public void keyInputEvent(InputEvent.KeyInputEvent event) {
        if(ClientProxy.keyUp.getIsKeyPressed()) {
            KeyBinding.setKeyBindState(ClientProxy.keyUp.getKeyCode(), false);
            WiringHelmetRender.setLastPressed("Up");
        }
        if(ClientProxy.keyDown.getIsKeyPressed()) {
            KeyBinding.setKeyBindState(ClientProxy.keyDown.getKeyCode(), false);
            WiringHelmetRender.setLastPressed("Down");
        }
        if(ClientProxy.keyLeft.getIsKeyPressed()) {
            KeyBinding.setKeyBindState(ClientProxy.keyLeft.getKeyCode(), false);
            WiringHelmetRender.setLastPressed("Left");
        }
        if(ClientProxy.keyRight.getIsKeyPressed()) {
            KeyBinding.setKeyBindState(ClientProxy.keyRight.getKeyCode(), false);
            WiringHelmetRender.setLastPressed("Right");
        }
    }
}
