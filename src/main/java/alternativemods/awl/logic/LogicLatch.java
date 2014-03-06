package alternativemods.awl.logic;

import alternativemods.awl.api.logic.ILogic;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Author: Lordmau5
 * Date: 06.03.14
 * Time: 15:14
 */
public class LogicLatch extends ILogic {

    boolean isPowered;

    @Override
    public boolean work(boolean isPowered) {
        return this.isPowered;
    }

    @Override
    public String getName() {
        return "Logic And";
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        this.isPowered = tag.getBoolean("powered");

        super.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setBoolean("powered", this.isPowered);

        super.writeToNBT(tag);
    }
}