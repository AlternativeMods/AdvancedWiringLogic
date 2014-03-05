package alternativemods.awl.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Author: Lordmau5
 * Date: 18.02.14
 * Time: 19:27
 */
public class Items {

    public static Item wireTool;
    public static Item wireHelmet;
    public static Item logicTool;

    public static void initiate() {
        createItems();
        registerItems();
    }

    private static void createItems() {
        wireTool = new ItemWiringTool();
        wireHelmet = new ItemWiringHelmet();
        logicTool = new ItemLogicTool();
    }

    private static void registerItems() {
        GameRegistry.registerItem(wireTool, "itemWiringTool");
        GameRegistry.registerItem(wireHelmet, "itemWiringHelmet");
        GameRegistry.registerItem(logicTool, "itemLogicTool");
    }

}