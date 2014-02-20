package alternativemods.awl;

import alternativemods.awl.util.Point;
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
 * Time: 19:16
 */
public class testWorld extends World {
    public testWorld(ISaveHandler p_i45368_1_, String p_i45368_2_, WorldProvider p_i45368_3_, WorldSettings p_i45368_4_, Profiler p_i45368_5_) {
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

    public void returnTest(int par1, int par2, int par3) {
        if(Main.wiresContainer.isWireStartingAt(this, new Point(par1, par2, par3)))
            Main.wiresContainer.notifyWireEnds(this, new Point(par1, par2, par3));
    }
}