package alternativemods.awl;

import alternativemods.awl.client.WiringHelmetRender;
import alternativemods.awl.item.Items;
import alternativemods.awl.util.Point;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 19:21
 */
public class EventHandler {

    @SubscribeEvent
    public void keyInputEvent(InputEvent.KeyInputEvent event) {
        if(Main.keyUp.getIsKeyPressed()) {
            KeyBinding.setKeyBindState(Main.keyUp.getKeyCode(), false);
            WiringHelmetRender.setLastPressed("Up");
        }
        if(Main.keyDown.getIsKeyPressed()) {
            KeyBinding.setKeyBindState(Main.keyDown.getKeyCode(), false);
            WiringHelmetRender.setLastPressed("Down");
        }
        if(Main.keyLeft.getIsKeyPressed()) {
            KeyBinding.setKeyBindState(Main.keyLeft.getKeyCode(), false);
            WiringHelmetRender.setLastPressed("Left");
        }
        if(Main.keyRight.getIsKeyPressed()) {
            KeyBinding.setKeyBindState(Main.keyRight.getKeyCode(), false);
            WiringHelmetRender.setLastPressed("Right");
        }
    }

    @SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent event) {
        EntityPlayer playertmp = Minecraft.getMinecraft().thePlayer;
        if(playertmp.worldObj == null || playertmp.worldObj.provider == null)
            return;

        ItemStack helmet = playertmp.getEquipmentInSlot(4);
        if(helmet == null || !(helmet.getItem() == Items.wireHelmet))
            return;

        WiringHelmetRender.render(playertmp, event.partialTicks);
    }

    @SubscribeEvent
    public void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(!event.player.worldObj.isRemote)
            return;

        Main.wireManager.abortCreation();
    }

    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event) {
        if(event.world.isRemote)
            return;

        Point brokenPoint = new Point(event.x, event.y, event.z);
        if(Main.wiresContainer.isWireStartingAt(event.world, brokenPoint))
            Main.wiresContainer.removeWires(brokenPoint);
        if(Main.wiresContainer.isWireEndingAt(event.world, brokenPoint))
            Main.wiresContainer.shortenWires(event.world, brokenPoint);
    }

}