package alternativemods.awl.util;

import alternativemods.awl.api.logic.LogicMain;

/**
 * Author: Lordmau5
 * Date: 18.02.14
 * Time: 20:19
 */
public class Point {

    public int x;
    public int y;
    public int z;

    protected LogicMain onLogic;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setLogic(LogicMain logic) {
        this.onLogic = logic;
    }

    public boolean equals(Point pt) {
        if(pt.x == this.x && pt.y == this.y && pt.z == this.z)
            return true;

        return false;
    }
}