package alternativemods.awl.manager;

import alternativemods.awl.logic.LogicMain;
import alternativemods.awl.util.Point;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 16:24
 */
public class LogicContainer {

    public List<LogicMain> logics;

    public LogicContainer() {
        this.logics = new ArrayList<LogicMain>();
    }

    public void addLogic(LogicMain logic) {
        if(!isLogicAtPos(logic.x, logic.y, logic.z, logic.dimension)){
            this.logics.add(logic);
        }
    }

    public void addLogic(LogicMain logic, World world, int x, int y, int z, int dimension) {
        if(isLogicAtPos(x, y, z, dimension))
            return;
        logic.setVars(world, x, y, z, dimension);
        this.logics.add(logic);
    }

    public void removeLogic(LogicMain logic) {
        for(int i=0; i<this.logics.size(); i++) {
            LogicMain lg = this.logics.get(i);
            if(lg.equals(logic)) {
                this.logics.remove(lg);
                break;
            }
        }
    }

    public boolean isLogicAtPos(Point point, int dimension) {
        for(LogicMain logic : this.logics)
            if(logic.positionEquals(point.x, point.y, point.z, dimension))
                return true;
        return false;
    }

    public boolean isLogicAtPos(int x, int y, int z, int dimension) {
        for(LogicMain logic : this.logics)
            if(logic.positionEquals(x, y, z, dimension))
                return true;
        return false;
    }

    public boolean isLogicPowered(LogicMain logic) {
        return logic.isPowered();
    }

    public boolean isLogicPowered(Point point, int dimension) {
        for(LogicMain logic : this.logics)
            if(logic.positionEquals(point.x, point.y, point.z, dimension))
                return logic.isPowered();
        return false;
    }

    public boolean isLogicPowered(int x, int y, int z, int dimension) {
        for(LogicMain logic : this.logics)
            if(logic.positionEquals(x, y, z, dimension))
                return logic.isPowered();
        return false;
    }

}