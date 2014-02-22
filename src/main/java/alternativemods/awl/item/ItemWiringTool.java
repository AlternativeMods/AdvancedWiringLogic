package alternativemods.awl.item;

import alternativemods.awl.Main;
import alternativemods.awl.logic.LogicNotGate;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 18.02.14
 * Time: 19:30
 */
public class ItemWiringTool extends Item {

    public ItemWiringTool() {
        this.setUnlocalizedName("awl.itemWiringTool");
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
        if(player.isSneaking()) {
            Main.wireManager.endWire();
            return is;
        }

        return is;
    }

    private void processWithKey(ItemStack is, EntityPlayer player, World world, int x, int y, int z) {
        Main.wireManager.activeLogic = new LogicNotGate();
        Main.proxy.addClientChat("Set active logic to \"" + Main.wireManager.activeLogic.getName() + "\".");
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        if(!world.isRemote)
            return true;

        if(Main.optionsKey.isPressed()) {
            processWithKey(is, player, world, x, y, z);
            return true;
        }

        if(Main.wireManager.activeLogic == null || Main.wireManager.activeLogic.getName() == "Logic Main") {
            Main.proxy.addClientChat("No logic selected! - Aborting!");
            return true;
        }

        if(player.isSneaking()) {
            Main.wireManager.endWire();
            return true;
        }

        if(!world.getBlock(x, y, z).isOpaqueCube()) {
            Main.proxy.addClientChat("Point has to be a full block! - Aborting!");
            return true;
        }
        if(Main.wireManager.doingWire)
            Main.wireManager.addPoint(x, y, z);
        else
            Main.wireManager.startWire(world, x, y, z, world.provider.dimensionId);

        return true;
    }

    @Override
    public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
        return false;
    }

}