package alternativemods.awl.block;

import alternativemods.awl.tiles.TileEntityLogic;
import cpw.mods.fml.common.registry.GameRegistry;

public class Blocks {

    public static BlockLogic blockLogic;

    public static void init(){
        blockLogic = new BlockLogic();

        GameRegistry.registerBlock(blockLogic, "blockLogic");

        GameRegistry.registerTileEntity(TileEntityLogic.class, "advancedWiringLogic.tileLogic");
    }
}
