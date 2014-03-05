package alternativemods.awl.item;

import alternativemods.awl.Main;
import alternativemods.awl.block.Blocks;
import alternativemods.awl.tiles.TileEntityLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:30
 */
public class ItemLogicTool extends Item {

    public ItemLogicTool() {
        this.setUnlocalizedName("awl.itemLogicTool");
        this.setCreativeTab(Main.tab_AWL);
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
        if(!world.isRemote)
            return true;

        if(player.isSneaking()) {
            Main.logicManager.setNextLogic();
            return true;
        }

        TileEntityLogic logicTile = new TileEntityLogic();
        logicTile.setLogic(Main.logicManager.getActiveLogic());

        ForgeDirection dr = ForgeDirection.getOrientation(side);
        x = x + dr.offsetX;
        y = y + dr.offsetY;
        z = z + dr.offsetZ;

        world.setBlock(x, y, z, Blocks.blockLogic);
        world.setTileEntity(x, y, z, logicTile);

        Main.proxy.addClientChat("Added a \"" + Main.logicManager.getActiveLogic().getName() + "\"!");

        return true;
    }
}