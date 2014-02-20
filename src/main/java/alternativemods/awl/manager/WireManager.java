package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.logic.LogicMain;
import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 18:48
 */
public class WireManager {

    public boolean doingWire;
    public int dimension = 0;
    public List<Point> points;
    public LogicMain activeLogic;

    public WireManager() {
        this.doingWire = false;
        this.points = null;
        this.activeLogic = null;
    }

    public void startWire(World world, int x, int y, int z, int dimension) {
        if(this.doingWire)
            return;

        this.dimension = dimension;

        this.doingWire = true;
        this.points = new ArrayList<Point>();
        this.points.add(new Point(x, y, z));
        Main.proxy.addClientChat("Starting a new wire with logic \"" + activeLogic.getName() + "\"!");
        this.activeLogic.setVars(world, x, y, z, dimension);
    }

    private boolean pointExists(Point point) {
        for(Point pt : this.points)
            if(pt.equals(point))
                return true;

        return false;
    }

    public void abortCreation() {
        this.doingWire = false;
        Main.proxy.addClientChat("Aborted wire-creation through dimension-change!");
    }

    public void addPoint(int x, int y, int z) {
        Point point = new Point(x, y, z);
        if(pointExists(point))
            return;

        this.points.add(point);
        Main.proxy.addClientChat("Added point to wire! - Got " + this.points.size() + " points now!");
    }

    public void endWire() {
        if(!this.doingWire)
            return;

        this.doingWire = false;
        if(this.points.size() < 2) {
            Main.proxy.addClientChat("Only got one point - Aborting wire-creation!");
            return;
        }

        Main.wiresContainer.addWire(new Wire(this.points, this.dimension));
        Main.proxy.addClientChat("Finished the wire with " + this.points.size() + " points!");
        this.points = null;
        Main.logicContainer.addLogic(this.activeLogic);
    }

}