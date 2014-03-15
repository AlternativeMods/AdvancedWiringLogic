package alternativemods.awl.api.util;

/**
 * Author: Lordmau5
 * Date: 06.03.14
 * Time: 11:27
 */
public abstract class IPoint {

    public int x;
    public int y;
    public int z;

    public IPoint() {}

    public IPoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(IPoint pt) {
        return pt.x == this.x && pt.y == this.y && pt.z == this.z;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "{" +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}