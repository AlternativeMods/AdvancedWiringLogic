package alternativemods.awl;

import alternativemods.awl.util.Point;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 19:16
 */
public class testWorld extends Block {
    protected testWorld(Material p_i45394_1_) {
        super(p_i45394_1_);
    }

    public void returnTest(World world, int par1, int par2, int par3, Block block) {
        if(Main.wiresContainer != null)
            if(Main.wiresContainer.isWireStartingAt(world, new Point(par1, par2, par3)))
                Main.wiresContainer.notifyWireEnds(world, new Point(par1, par2, par3));
    }
}