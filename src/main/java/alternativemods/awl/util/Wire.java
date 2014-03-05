package alternativemods.awl.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 18:41
 */
public class Wire {

    public int dimension;
    public List<Point> points;

    protected boolean isPowered;

    public Wire(Point point, int dimension) {
        this.points = new ArrayList<Point>();
        this.points.add(point);
        this.dimension = dimension;
        this.isPowered = false;
    }

    public Wire(List<Point> points, int dimension) {
        this.points = points;
        this.dimension = dimension;
        this.isPowered = false;
    }

    public boolean isPowered() {
        return isPowered;
    }

    public void setPowered(boolean isPowered) {
        this.isPowered = isPowered;
    }

}