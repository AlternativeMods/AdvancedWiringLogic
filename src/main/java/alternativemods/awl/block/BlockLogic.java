package alternativemods.awl.block;

import alternativemods.awl.Main;
import alternativemods.awl.api.logic.AbstractLogic;
import alternativemods.awl.item.Items;
import alternativemods.awl.tiles.TileEntityLogic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:11
 */
public class BlockLogic extends Block {

    protected BlockLogic(){
        super(Material.iron);

        this.setBlockName("awl.blockLogic");
        this.setHardness(1.0F);
    }

    public boolean hasTileEntity(int metadata){
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata){
        return new TileEntityLogic();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if(world.isRemote) return true;

        if(player.getHeldItem() != null && player.getHeldItem().getItem() == Items.wireTool){
            if(Main.wireManager.doingWire){
                Main.wireManager.addPoint(x, y, z);
            }else{
                Main.wireManager.startWire(x, y, z, world.provider.dimensionId);
            }
            return true;
        }

        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile == null || !(tile instanceof TileEntityLogic)){
            return true;
        }

        AbstractLogic logic = ((TileEntityLogic) tile).getLogic();
        player.addChatMessage(new ChatComponentText("powered: " + logic.isPowered()));
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta){
        Main.logicContainer.removeLogic(x, y, z, world.provider.dimensionId);

        super.breakBlock(world, x, y, z, block, meta);
    }
}
