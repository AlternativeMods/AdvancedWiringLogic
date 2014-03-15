package alternativemods.awl.client;

import alternativemods.awl.Main;
import alternativemods.awl.api.util.AbstractPoint;
import alternativemods.awl.block.Blocks;
import alternativemods.awl.tiles.TileEntityLogic;
import alternativemods.awl.util.Wire;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 19:20
 */
public class WiringHelmetRender {

    private static Minecraft mc = Minecraft.getMinecraft();
    private static String lastPressed = "None";

    private static float calcDistance(float x, float y, float z, AbstractPoint point) {
        float dist = 0;

        dist += x > point.x ? x - point.x : point.x - x;
        dist += y > point.y ? y - point.y : point.y - y;
        dist += z > point.z ? z - point.z : point.z- z;

        return dist;
    }

    public static void setLastPressed(String lpr) {
        lastPressed = lpr;
    }

    private static String getLookingBlockName(EntityPlayer player) {
        if(mc.objectMouseOver == null || player.worldObj.isAirBlock(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ))
            return "Not looking at a block.";

        Block block = player.worldObj.getBlock(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
        if(block == Blocks.blockLogic) {
            TileEntityLogic logic = (TileEntityLogic) player.worldObj.getTileEntity(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
            if(logic == null || logic.getLogic() == null || logic.getLogic().getName() == null)
                return "Logic";
            return logic.getLogic().getName();
        }
        Item item = block.getItem(player.worldObj, mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
        ItemStack is = new ItemStack(item, 1, player.worldObj.getBlockMetadata(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ));
        return is.getDisplayName();
    }

    public static void render(EntityPlayer playertmp, float partialTicks) {
        float posX = (float)playertmp.posX;
        float posY = (float)playertmp.posY;
        float posZ = (float)playertmp.posZ;

        float pposX = (float)playertmp.prevPosX;
        float pposY = (float)playertmp.prevPosY;
        float pposZ = (float)playertmp.prevPosZ;

        float px = pposX + (posX - pposX) * partialTicks;
        float py = pposY + (posY - pposY) * partialTicks;
        float pz = pposZ + (posZ - pposZ) * partialTicks;

        if (!Main.wiresContainer.wires.isEmpty())
        {
            GL11.glPushMatrix();

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(1, 1, 0, 1);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glLineWidth(2);

            GL11.glEnable(GL11.GL_LINE_SMOOTH);

            GL11.glBegin(GL11.GL_LINES);

            for (int i = 0; i < Main.wiresContainer.wires.size(); i++) {
                Wire wire = Main.wiresContainer.wires.get(i);
                if(playertmp.worldObj.provider.dimensionId == wire.dimension) {
                    if(!wire.points.isEmpty()) {
                        int i2 = 0;
                        while(i2 < wire.points.size() - 1) {
                            AbstractPoint tmp1 = wire.points.get(i2);
                            AbstractPoint tmp2 = wire.points.get(i2 + 1);

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

            GL11.glBegin(GL11.GL_LINES);

            int i = 0;
            while(i < Main.wireManager.points.size() - 1) {
                AbstractPoint tmp1 = Main.wireManager.points.get(i);
                AbstractPoint tmp2 = Main.wireManager.points.get(i + 1);

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

        String textLeft = getLookingBlockName(playertmp);
        String textRight = lastPressed;

        FontRenderer rnd = Minecraft.getMinecraft().fontRenderer;

        //Interpolate the angles
        float yaw = playertmp.prevRotationYaw + (playertmp.rotationYaw - playertmp.prevRotationYaw) * partialTicks;
        float pitch = playertmp.prevRotationPitch + (playertmp.rotationPitch - playertmp.prevRotationPitch) * partialTicks;

        GL11.glPushMatrix();

        //Rotate the HUD for our head rotation
        // Left Box
        GL11.glRotatef(180 - yaw, 0, 1, 0);
        GL11.glRotatef(- pitch, 1, 0, 0);
        GL11.glTranslatef(-1.4f, -0.25f, -0.75f);
        GL11.glRotatef(30, 0, 1, 0);

        //Start drawing quads
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4d(0.0, 0.5, 0.5, 0.4);
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
        GL11.glScalef(0.01f, 0.01f, 0.01f);
        GL11.glRotatef(180, 1, 1, 0);
        GL11.glRotatef(90, 0, 0, 1);
        GL11.glTranslatef(0, 0, -0.01f); //Translate a little bit forward to prevent z-fighting
        rnd.drawString(textLeft, 5, -90, 0xFFFFFFFF);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();

        //-------------------------------------
        // Right Box
        GL11.glPushMatrix();

        GL11.glRotatef(180 - yaw, 0, 1, 0);
        GL11.glRotatef(- pitch, 1, 0, 0);
        GL11.glTranslatef(0.52f, -0.25f, -1.25f);
        GL11.glRotatef(-30, 0, 1, 0);

        //Start drawing quads
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4d(0.0, 0.5, 0.5, 0.4);
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
        GL11.glScalef(0.01f, 0.01f, 0.01f);
        GL11.glRotatef(180, 1, 1, 0);
        GL11.glRotatef(90, 0, 0, 1);
        GL11.glTranslatef(0, 0, -0.01f); //Translate a little bit forward to prevent z-fighting
        rnd.drawString(textRight, 95 - rnd.getStringWidth(textRight), -90, 0xFFFFFFFF);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();
    }
}
