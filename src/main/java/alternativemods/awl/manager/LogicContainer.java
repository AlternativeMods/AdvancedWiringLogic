package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.api.logic.ILogic;
import alternativemods.awl.api.util.IPoint;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 16:24
 */
public class LogicContainer {

    public List<ILogic> logics;

    public LogicContainer() {
        this.logics = new ArrayList<ILogic>();
    }

    public void addLogic(ILogic logic) {
        if(!isLogicAtPos(logic.x, logic.y, logic.z, logic.dimension))
            this.logics.add(logic);
    }

    public void removeLogic(World world, int x, int y, int z, int dimension) {
        for(int i=0; i<this.logics.size(); i++) {
            ILogic lg = this.logics.get(i);
            if(lg.positionEquals(x, y, z, dimension)) {
                this.logics.remove(lg);
                Main.wiresContainer.removeLogic(world, lg);
                break;
            }
        }
    }

    public ILogic getLogicFromPosition(int x, int y, int z, int dimension) {
        for(ILogic logic : this.logics)
            if(logic.positionEquals(x, y, z, dimension))
                return logic;
        return null;
    }

    public boolean isLogicAtPos(IPoint point, int dimension) {
        for(ILogic logic : this.logics)
            if(logic.equals(point) && logic.dimension == dimension)
                return true;
        return false;
    }

    public boolean isLogicAtPos(int x, int y, int z, int dimension) {
        for(ILogic logic : this.logics) {
            if(logic.positionEquals(x, y, z, dimension))
                return true;
        }
        return false;
    }

}