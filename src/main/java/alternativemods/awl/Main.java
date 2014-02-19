package alternativemods.awl;

import alternativemods.awl.item.Items;
import alternativemods.awl.manager.WireManager;
import alternativemods.awl.manager.WiresContainer;
import alternativemods.awl.proxy.CommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

/**
 * Author: Lordmau5
 * Date: 18.02.14
 * Time: 19:25
 */
@Mod(modid="AWL", name="Advanced Wiring Logic", version = "1.0")
public class Main {

    public static CreativeTabs tab_AWL = new CreativeTabs("AWL") {
        @Override
        public Item getTabIconItem() {
            return Items.wireTool;
        }
    };

    public static WiresContainer wiresContainer;
    public static WireManager wireManager;

    @SidedProxy(modId = "AWL", clientSide = "alternativemods.awl.proxy.ClientProxy", serverSide = "alternativemods.awl.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Items.initiate();

        MinecraftForge.EVENT_BUS.register(new EventHandler());
        FMLCommonHandler.instance().bus().register(new EventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        wireManager = new WireManager();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        wiresContainer = new WiresContainer();
        wireManager = new WireManager();
    }

}