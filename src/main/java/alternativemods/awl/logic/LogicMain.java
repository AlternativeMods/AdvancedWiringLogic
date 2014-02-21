package alternativemods.awl.logic;

import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 15:28
 */
public class LogicMain {

    protected World world;
    public int x;
    public int y;
    public int z;
    public int dimension;

    public void setVars(World world, int x, int y, int z, int dimension) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public int[] getPosition() {
        return new int[] {this.x, this.y, this.z, this.dimension};
    }

    public boolean equals(LogicMain logic) {
        return this.getPosition() == logic.getPosition();
    }

    public boolean positionEquals(int x, int y, int z, int dimension) {
        return x == this.x && y == this.y && z == this.z && this.dimension == dimension;
    }

    public boolean isPowered() {
        return false;
    }

    public String getName() {
        return "Logic Main";
    }

}