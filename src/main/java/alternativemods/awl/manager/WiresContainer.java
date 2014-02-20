package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;
import net.minecraft.world.World;

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

    public boolean isWireStartingAt(World world, Point point) {
        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(0).equals(point))
                return true;
        return false;
    }

    public void notifyWireEnds(World world, Point point) {
        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(0).equals(point)) {
                Point endPt = wire.points.get(wire.points.size() - 1);
                world.notifyBlocksOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
            }
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
                    System.out.println(Main.logicContainer.isLogicPowered(startPoint, dimension));
                    return Main.logicContainer.isLogicPowered(startPoint, dimension);
                }
            }
        }

        return false;
    }

}