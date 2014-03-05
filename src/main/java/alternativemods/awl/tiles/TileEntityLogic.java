package alternativemods.awl.tiles;

import alternativemods.awl.Main;
import alternativemods.awl.api.logic.LogicMain;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:15
 */
public class TileEntityLogic extends TileEntity {

    protected LogicMain logic;

    public TileEntityLogic() {}
    public TileEntityLogic(LogicMain logic) {
        this.logic = logic;
    }

    public void setLogic(LogicMain logic) {
        this.logic = logic;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.logic = Main.logicRegister.getLogicFromName(tag.getString("Logic"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setString("Logic", this.logic.getName());
    }
}