package alternativemods.awl.block;

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
}