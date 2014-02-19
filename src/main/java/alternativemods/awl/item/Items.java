package alternativemods.awl.item;

import alternativemods.awl.Main;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Author: Lordmau5
 * Date: 18.02.14
 * Time: 19:27
 */
public class Items {

    public static Item wireTool;

    public static void initiate() {
        wireTool = new ItemWiringTool();

        addToCreative();
        register();
    }

    private static void addToCreative() {
        wireTool.setCreativeTab(Main.tab_AWL);
    }

    private static void register() {
        GameRegistry.registerItem(wireTool, "itemWiringTool");
    }

}