package alternativemods.awl.block;

import alternativemods.awl.Main;
import alternativemods.awl.tiles.TileEntityLogic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:11
 */
public class BlockLogic extends Block {

    protected BlockLogic() {
        super(Material.iron);

        this.setBlockName("awl.blockLogic");
        this.setHardness(1.0F);
    }

    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityLogic();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        Main.logicContainer.removeLogic(world, x, y, z, world.provider.dimensionId);

        super.breakBlock(world, x, y, z, block, meta);
    }
}