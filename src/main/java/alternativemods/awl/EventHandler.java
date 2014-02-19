package alternativemods.awl;

import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 19:21
 */
public class EventHandler {

    @SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent event) {
        EntityPlayer playertmp = Minecraft.getMinecraft().thePlayer;

        float posX = (float)playertmp.posX;
        float posY = (float)playertmp.posY;
        float posZ = (float)playertmp.posZ;

        float pposX = (float)playertmp.prevPosX;
        float pposY = (float)playertmp.prevPosY;
        float pposZ = (float)playertmp.prevPosZ;

        float px = pposX + (posX - pposX) * event.partialTicks;
        float py = pposY + (posY - pposY) * event.partialTicks;
        float pz = pposZ + (posZ - pposZ) * event.partialTicks;

        if (!Main.wiresContainer.wires.isEmpty())
        {
            GL11.glPushMatrix();

            GL11.glDisable(3553);
            GL11.glColor4f(1, 0, 0, 1);
            GL11.glEnable(3042);
            GL11.glLineWidth(10);

            GL11.glEnable(2848);

            GL11.glBegin(1);

            for (int i = 0; i < Main.wiresContainer.wires.size(); i++) {
                Wire wire = Main.wiresContainer.wires.get(i);
                if(playertmp.worldObj.provider.dimensionId == wire.dimension) {
                    if(!wire.points.isEmpty()) {
                        int i2 = 0;
                        while(i2 < wire.points.size() - 1) {
                            Point tmp1 = wire.points.get(i2);
                            Point tmp2 = wire.points.get(i2 + 1);
                            GL11.glVertex3f((float)(tmp1.x - px + 0.5), (float) (tmp1.y - py + 0.5), (float)(tmp1.z - pz + 0.5));
                            GL11.glVertex3f((float)(tmp2.x - px + 0.5), (float) (tmp2.y - py + 0.5), (float)(tmp2.z - pz + 0.5));
                            i2++;
                        }
                    }
                }
            }

            GL11.glEnd();

            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
        if (!Main.wireManager.points.isEmpty() && playertmp.worldObj.provider.dimensionId == Main.wireManager.dimension) {
            GL11.glPushMatrix();

            GL11.glDisable(3553);
            GL11.glColor4d(1, 1, 1, 0.25);
            GL11.glEnable(3042);
            GL11.glLineWidth(10);

            GL11.glEnable(2848);

            GL11.glBegin(1);

            int i = 0;
            while(i < Main.wireManager.points.size() - 1) {
                Point tmp1 = Main.wireManager.points.get(i);
                Point tmp2 = Main.wireManager.points.get(i + 1);
                GL11.glVertex3f((float)(tmp1.x - px + 0.5), (float) (tmp1.y - py + 0.5), (float)(tmp1.z - pz + 0.5));
                GL11.glVertex3f((float)(tmp2.x - px + 0.5), (float) (tmp2.y - py + 0.5), (float)(tmp2.z - pz + 0.5));
                i++;
            }

            GL11.glEnd();

            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent
    public void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(!event.player.worldObj.isRemote)
            return;

        Main.wireManager.abortCreation();
    }

}