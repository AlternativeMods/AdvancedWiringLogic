package alternativemods.awl;

import alternativemods.awl.block.Blocks;
import alternativemods.awl.item.Items;
import alternativemods.awl.manager.LogicContainer;
import alternativemods.awl.manager.LogicManager;
import alternativemods.awl.manager.WireManager;
import alternativemods.awl.manager.WiresContainer;
import alternativemods.awl.network.NetworkHandler;
import alternativemods.awl.proxy.CommonProxy;
import alternativemods.awl.register.LogicRegister;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

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

    public static KeyBinding keyUp = new KeyBinding("Up", Keyboard.KEY_UP, "Advanced Wiring Logic");
    public static KeyBinding keyDown = new KeyBinding("Down", Keyboard.KEY_DOWN, "Advanced Wiring Logic");
    public static KeyBinding keyLeft = new KeyBinding("Left", Keyboard.KEY_LEFT, "Advanced Wiring Logic");
    public static KeyBinding keyRight = new KeyBinding("Right", Keyboard.KEY_RIGHT, "Advanced Wiring Logic");

    public static WiresContainer wiresContainer;
    public static WireManager wireManager;
    public static LogicContainer logicContainer;
    public static LogicRegister logicRegister;
    public static LogicManager logicManager;

    @SidedProxy(modId = "AWL", clientSide = "alternativemods.awl.proxy.ClientProxy", serverSide = "alternativemods.awl.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkHandler.registerChannels(event.getSide());
        logicRegister = new LogicRegister();

        Items.initiate();
        Blocks.initiate();

        proxy.init();

        MinecraftForge.EVENT_BUS.register(new EventHandler());
        FMLCommonHandler.instance().bus().register(new EventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        wireManager = new WireManager();
        logicManager = new LogicManager();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        wiresContainer = new WiresContainer();
        wireManager = new WireManager();
        logicContainer = new LogicContainer();
        logicManager = new LogicManager();
    }

}
