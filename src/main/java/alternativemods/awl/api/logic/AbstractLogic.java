package alternativemods.awl.api.logic;

import alternativemods.awl.api.util.AbstractPoint;
import alternativemods.awl.util.Wire;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: Lordmau5
 * Date: 06.03.14
 * Time: 11:22
 */
public abstract class AbstractLogic extends AbstractPoint {

    protected World world;
    public int dimension;
    protected boolean isPowered;

    public void setVars(World world, int x, int y, int z, int dimension) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.world = world;
        this.dimension = dimension;
    }

    public void setPowered(boolean powered) {
        this.isPowered = powered;
    }

    public boolean isPowered() {
        return this.isPowered;
    }
    
    public boolean canAddLogic() {
    	return true;
    }
    
    public String getAddError() {
    	return "NONE";
    }
    
    public boolean setupWith(Wire wire) {
    	return true;
    }

    public void work(boolean powered) {
        this.isPowered = powered;
    }

    public int[] getPosition(){
        return new int[] {this.x, this.y, this.z, this.dimension};
    }

    public boolean positionEquals(int x, int y, int z, int dimension) {
        return x == this.x && y == this.y && z == this.z && this.dimension == dimension;
    }

    public abstract String getName();

    public void readFromNBT(NBTTagCompound tag) {
        tag.setBoolean("redstoneState", this.isPowered);
    }

    public void writeToNBT(NBTTagCompound tag) {
        this.isPowered = tag.getBoolean("redstoneState");
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof AbstractLogic)) return false;
        AbstractLogic that = (AbstractLogic) o;
        return x == that.x && y == that.y && z == that.z && this.dimension == that.dimension;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("world", world)
                .append("dimension", dimension)
                .append("isPowered", isPowered)
                .toString();
    }
}
