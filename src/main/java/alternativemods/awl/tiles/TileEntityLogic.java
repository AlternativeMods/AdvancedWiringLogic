package alternativemods.awl.tiles;

import alternativemods.awl.Main;
import alternativemods.awl.api.logic.AbstractLogic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:15
 */
public class TileEntityLogic extends TileEntity {

    protected AbstractLogic logic;
    boolean needsSetup = true;

    public TileEntityLogic() {
        this.needsSetup = true;
    }
    public TileEntityLogic(AbstractLogic logic) {
        super();
        this.logic = logic;
    }

    public AbstractLogic getLogic() { return this.logic; }
    public void setLogic(AbstractLogic logic) {
        this.logic = logic;
    }

    @Override
    public void validate() {
        super.validate();
    }

    @Override
    public void updateEntity() {
        if(this.needsSetup && this.worldObj != null && !this.worldObj.isRemote) {
            this.needsSetup = false;
            this.logic.setVars(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
            Main.logicContainer.addLogic(this.logic);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.logic = Main.logicRegister.getLogicFromName(tag.getString("Logic"));
        this.logic.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        if(this.logic != null) {
            tag.setString("Logic", this.logic.getName());
            this.logic.writeToNBT(tag);
        }
    }
}