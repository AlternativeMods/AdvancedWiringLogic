package alternativemods.awl.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 18:41
 */
public class Wire {

    public List<Point> points;

    public Wire(Point point) {
        this.points = new ArrayList<Point>();
        this.points.add(point);
    }

    public Wire(List<Point> points) {
        this.points = points;
    }

}