package alternativemods.awl.item;

import alternativemods.awl.Main;
import net.minecraft.item.ItemArmor;

/**
 * Author: Lordmau5
 * Date: 21.02.14
 * Time: 19:33
 */
public class ItemWiringHelmet extends ItemArmor {

    public ItemWiringHelmet() {
        super(ArmorMaterial.DIAMOND, 0, 0);
        this.setUnlocalizedName("awl.itemWiringHelmet");

        this.setCreativeTab(Main.tab_AWL);
    }
}