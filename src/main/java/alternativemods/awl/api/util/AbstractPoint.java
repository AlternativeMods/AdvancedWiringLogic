package alternativemods.awl.api.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: Lordmau5
 * Date: 06.03.14
 * Time: 11:27
 */
public abstract class AbstractPoint {

    public int x;
    public int y;
    public int z;

    public AbstractPoint(){}
    public AbstractPoint(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("x", x)
                .append("y", y)
                .append("z", z)
                .toString();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof AbstractPoint)) return false;
        AbstractPoint that = (AbstractPoint) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode(){
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
