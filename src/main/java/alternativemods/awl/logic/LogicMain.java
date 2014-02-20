package alternativemods.awl.logic;

import alternativemods.awl.Main;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 15:28
 */
public class LogicMain {

    protected World world;
    protected int x;
    protected int y;
    protected int z;
    protected int dimension;

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

    class testClass extends World {

        public testClass(ISaveHandler p_i45368_1_, String p_i45368_2_, WorldProvider p_i45368_3_, WorldSettings p_i45368_4_, Profiler p_i45368_5_) {
            super(p_i45368_1_, p_i45368_2_, p_i45368_3_, p_i45368_4_, p_i45368_5_);
        }

        @Override
        protected IChunkProvider createChunkProvider() {
            return null;
        }

        @Override
        public Entity getEntityByID(int var1) {
            return null;
        }

        public int returnTest(int par1, int par2, int par3) {
            if(Main.wiresContainer.isBlockPoweredByLogic(par1, par2, par3, this.provider.dimensionId))
                return 15;

            return 0;
        }
    }

}