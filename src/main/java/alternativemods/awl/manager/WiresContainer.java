package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 18:49
 */
public class WiresContainer {

    public List<Wire> wires;

    public WiresContainer() {
        this.wires = new ArrayList<Wire>();
    }

    public void addWire(Wire wire) {
        this.wires.add(wire);
    }

    public void removeWire(Wire wire) {
        this.wires.remove(wire);
    }

    public boolean isWireStartingAt(Point point) {
        for(Wire wire : this.wires)
            if(wire.points.get(0).equals(point))
                return true;
        return false;
    }

    public boolean isBlockPoweredByLogic(int x, int y, int z, int dimension) {
        Point pt = new Point(x, y, z);
        if(this.wires.isEmpty())
            return false;

        for(Wire wire : this.wires) {
            Point wirePt = wire.points.get(wire.points.size() - 1);
            if(wirePt.equals(pt)) {
                Point startPoint = wire.points.get(0);
                if(Main.logicContainer.isLogicAtPos(startPoint, dimension)) {
                    System.out.println("MEEP");
                    return Main.logicContainer.isLogicPowered(startPoint, dimension);
                }
            }
        }

        return false;
    }

}