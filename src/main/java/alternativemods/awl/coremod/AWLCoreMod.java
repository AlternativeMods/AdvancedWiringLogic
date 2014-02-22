package alternativemods.awl.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 16:47
 */
@IFMLLoadingPlugin.Name("AWL|Core")
@IFMLLoadingPlugin.MCVersion("1.7.2")
@IFMLLoadingPlugin.TransformerExclusions({"alternativemods.awl.coremod.transformers."})
@SuppressWarnings("unused")
public class AWLCoreMod implements IFMLLoadingPlugin {

    public static boolean obfuscated;

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                    "alternativemods.awl.coremod.transformers.WorldTransformer",
        };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        obfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}