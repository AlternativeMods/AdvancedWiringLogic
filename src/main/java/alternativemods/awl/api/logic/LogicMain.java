package alternativemods.awl.api.logic;

import alternativemods.awl.util.Point;
import net.minecraft.world.World;

import java.util.Arrays;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 15:28
 */
public abstract class LogicMain {

    protected World world;
    public int x;
    public int y;
    public int z;
    public int dimension;

    public boolean work(boolean isPowered) {
        return isPowered;
    }

    public void setVars(World world, int x, int y, int z, int dimension) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public int[] getPosition(){
        return new int[] {this.x, this.y, this.z, this.dimension};
    }

    public boolean equals(LogicMain logic) {
        return Arrays.equals(this.getPosition(), logic.getPosition());
    }

    public boolean positionEquals(int x, int y, int z, int dimension) {
        return x == this.x && y == this.y && z == this.z && this.dimension == dimension;
    }

    public boolean positionEquals(Point point, int dimension) {
        return point.x == this.x && point.y == this.y && point.z == this.z && this.dimension == dimension;
    }

    public String getName() {
        return "Logic Main";
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
