package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 18:48
 */
public class WireManager {

    public boolean doingWire;
    private int dimension;
    public List<Point> points;

    public void startWire(int x, int y, int z, int dimension) {
        if(this.doingWire)
            return;

        this.dimension = dimension;

        this.doingWire = true;
        this.points = new ArrayList<Point>();
        this.points.add(new Point(x, y, z));
        Main.proxy.addClientChat("Starting a new wire!");
    }

    private boolean pointExists(Point point) {
        for(Point pt : this.points)
            if(pt.equals(point))
                return true;

        return false;
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
    }

}