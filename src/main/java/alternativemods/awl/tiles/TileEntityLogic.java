package alternativemods.awl.tiles;

import alternativemods.awl.Main;
import alternativemods.awl.logic.ILogic;
import alternativemods.awl.logic.LogicMain;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:15
 */
public class TileEntityLogic extends TileEntity {

    protected ILogic logic;

    public TileEntityLogic() {}

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