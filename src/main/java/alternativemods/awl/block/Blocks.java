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
        GameRegistry.registerTileEntity(TileEntityLogic.class, "tileLogic");
    }

}