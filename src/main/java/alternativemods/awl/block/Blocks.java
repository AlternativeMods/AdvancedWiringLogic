package alternativemods.awl.block;

import alternativemods.awl.tiles.TileEntityLogic;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:12
 */
public class Blocks {

    public static BlockLogic blockLogic;

    public static void initiate() {
        createBlocks();
        registerBlocks();
        registerTiles();
    }

    protected static void createBlocks() {
        blockLogic = new BlockLogic();
    }

    protected static void registerBlocks() {
        GameRegistry.registerBlock(blockLogic, "blockLogic");
    }

    protected static void registerTiles() {
        //TileEntity names are global. When we are installed with a mod that adds a 'tileLogic' too, it will conflict
        //That's why i added our mod name before it, so it won't make conflicts
        GameRegistry.registerTileEntity(TileEntityLogic.class, "advancedWiringLogic.tileLogic");
    }
}
