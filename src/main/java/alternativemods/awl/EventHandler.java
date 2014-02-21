package alternativemods.awl;

import alternativemods.awl.item.Items;
import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 19:21
 */
public class EventHandler {

    private float calcDistance(float x, float y, float z, Point point) {
        float dist = 0;

        dist += x > point.x ? x - point.x : point.x - x;
        dist += y > point.y ? y - point.y : point.y - y;
        dist += z > point.z ? z - point.z : point.z- z;

        return dist;
    }

    @SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent event) {
        EntityPlayer playertmp = Minecraft.getMinecraft().thePlayer;
        if(playertmp.worldObj == null || playertmp.worldObj.provider == null)
            return;

        ItemStack helmet = playertmp.getEquipmentInSlot(4);
        if(helmet == null || !(helmet.getItem() == Items.wireHelmet))
            return;

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

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(0, 1, 0, 1);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glLineWidth(2);

            GL11.glEnable(GL11.GL_LINE_SMOOTH);

            GL11.glBegin(1);

            for (int i = 0; i < Main.wiresContainer.wires.size(); i++) {
                Wire wire = Main.wiresContainer.wires.get(i);
                if(playertmp.worldObj.provider.dimensionId == wire.dimension) {
                    if(!wire.points.isEmpty()) {
                        int i2 = 0;
                        while(i2 < wire.points.size() - 1) {
                            Point tmp1 = wire.points.get(i2);
                            Point tmp2 = wire.points.get(i2 + 1);

                            if(calcDistance(px, py, pz, tmp1) < 20) {
                                GL11.glVertex3f((float)(tmp1.x - px + 0.5), (float) (tmp1.y - py + 0.5), (float)(tmp1.z - pz + 0.5));
                                GL11.glVertex3f((float)(tmp2.x - px + 0.5), (float) (tmp2.y - py + 0.5), (float)(tmp2.z - pz + 0.5));
                            }

                            i2++;
                        }
                    }
                }
            }

            GL11.glEnd();

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();
        }
        if (Main.wireManager != null && Main.wireManager.points != null && !Main.wireManager.points.isEmpty() && playertmp.worldObj.provider.dimensionId == Main.wireManager.dimension) {
            GL11.glPushMatrix();

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4d(1, 1, 1, 0.25);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glLineWidth(2);

            GL11.glEnable(GL11.GL_LINE_SMOOTH);

            GL11.glBegin(1);

            int i = 0;
            while(i < Main.wireManager.points.size() - 1) {
                Point tmp1 = Main.wireManager.points.get(i);
                Point tmp2 = Main.wireManager.points.get(i + 1);

                if(calcDistance(px, py, pz, tmp1) < 20) {
                    GL11.glVertex3f((float)(tmp1.x - px + 0.5), (float) (tmp1.y - py + 0.5), (float)(tmp1.z - pz + 0.5));
                    GL11.glVertex3f((float)(tmp2.x - px + 0.5), (float) (tmp2.y - py + 0.5), (float)(tmp2.z - pz + 0.5));
                }

                i++;
            }

            GL11.glEnd();

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();
        }

        //Interpolate the angles
        float yaw = playertmp.prevRotationYaw + (playertmp.rotationYaw - playertmp.prevRotationYaw) * event.partialTicks;
        float pitch = playertmp.prevRotationPitch + (playertmp.rotationPitch - playertmp.prevRotationPitch) * event.partialTicks;

        GL11.glPushMatrix();

        //Rotate the HUD for our head rotation
        GL11.glRotatef(180 - yaw, 0, 1, 0);
        GL11.glRotatef(- pitch, 1, 0, 0);
        GL11.glTranslatef(-0.5f, -0.5f, -1);

        //Start drawing quads
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4d(0.3, 0.3, 0.3, 0.4);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBegin(GL11.GL_QUADS);
        //Draw inner part of the quad
        GL11.glVertex2d(0, 0);
        GL11.glVertex2d(1, 0);
        GL11.glVertex2d(1, 1);
        GL11.glVertex2d(0, 1);
        //Draw outer part of the quad
        GL11.glVertex2d(1, 0);
        GL11.glVertex2d(0, 0);
        GL11.glVertex2d(0, 1);
        GL11.glVertex2d(1, 1);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();
    }

    @SubscribeEvent
    public void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(!event.player.worldObj.isRemote)
            return;

        Main.wireManager.abortCreation();
    }

}