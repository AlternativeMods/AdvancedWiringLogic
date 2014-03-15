package alternativemods.awl.util;

import alternativemods.awl.api.util.AbstractPoint;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 18:41
 */
public class Wire {

    public int dimension;
    public List<AbstractPoint> points;
    protected boolean powered = false;

    public Wire(AbstractPoint point, int dimension) {
        this.points = Lists.newArrayList();
        this.points.add(point);
        this.dimension = dimension;
    }

    public Wire(List<AbstractPoint> points, int dimension) {
        this.points = points;
        this.dimension = dimension;
        this.powered = false;
    }

    public boolean isPowered() {
        return powered;
    }

    public void setPowered(boolean isPowered) {
        this.powered = isPowered;
    }

    public AbstractPoint getEndPoint(){ //TODO: this is a very dirty way of doing it. Add a field for this later
        return this.points.get(this.points.size() - 1);
    }
}
