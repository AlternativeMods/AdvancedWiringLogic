package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.api.logic.AbstractLogic;
import alternativemods.awl.api.util.AbstractPoint;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 16:24
 */
public class LogicContainer {

    public List<AbstractLogic> logics;

    public LogicContainer() {
        this.logics = new ArrayList<AbstractLogic>();
    }

    public void addLogic(AbstractLogic logic) {
        if(!isLogicAtPos(logic.x, logic.y, logic.z, logic.dimension))
            this.logics.add(logic);
    }

    public void removeLogic(World world, int x, int y, int z, int dimension) {
        for(int i=0; i<this.logics.size(); i++) {
            AbstractLogic lg = this.logics.get(i);
            if(lg.positionEquals(x, y, z, dimension)) {
                this.logics.remove(lg);
                Main.wiresContainer.removeLogic(world, lg);
                break;
            }
        }
    }

    public AbstractLogic getLogicFromPosition(int x, int y, int z, int dimension) {
        for(AbstractLogic logic : this.logics)
            if(logic.positionEquals(x, y, z, dimension))
                return logic;
        return null;
    }

    public boolean isLogicAtPos(AbstractPoint point, int dimension) {
        for(AbstractLogic logic : this.logics)
            if(logic.equals(point) && logic.dimension == dimension)
                return true;
        return false;
    }

    public boolean isLogicAtPos(int x, int y, int z, int dimension) {
        for(AbstractLogic logic : this.logics) {
            if(logic.positionEquals(x, y, z, dimension))
                return true;
        }
        return false;
    }

}