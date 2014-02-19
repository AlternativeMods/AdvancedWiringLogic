package alternativemods.awl.util;

/**
 * Author: Lordmau5
 * Date: 18.02.14
 * Time: 20:19
 */
public class Point {

    public int x;
    public int y;
    public int z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Point pt) {
        if(pt.x == this.x && pt.y == this.y && pt.z == this.z)
            return true;

        return false;
    }
}