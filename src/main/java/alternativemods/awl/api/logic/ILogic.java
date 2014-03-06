package alternativemods.awl.api.logic;

import alternativemods.awl.api.util.IPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Arrays;

/**
 * Author: Lordmau5
 * Date: 06.03.14
 * Time: 11:22
 */
public abstract class ILogic extends IPoint {
    protected World world;
    public int dimension;

    public void setVars(World world, int x, int y, int z, int dimension) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.world = world;
        this.dimension = dimension;
    }

    public boolean work(boolean isPowered) {
        return isPowered;
    }

    public int[] getPosition(){
        return new int[] {this.x, this.y, this.z, this.dimension};
    }

    public boolean equals(ILogic logic) {
        return Arrays.equals(this.getPosition(), logic.getPosition()) && this.getName().equals(logic.getName());
    }

    public boolean positionEquals(int x, int y, int z, int dimension) {
        return x == this.x && y == this.y && z == this.z && this.dimension == dimension;
    }

    public abstract String getName();

    public void readFromNBT(NBTTagCompound tag) {

    }

    public void writeToNBT(NBTTagCompound tag) {

    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "{" +
                "world=" + world +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", dimension=" + dimension +
                '}';
    }
}