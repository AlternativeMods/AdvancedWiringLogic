package alternativemods.awl.logic;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 15:44
 */
public class LogicRedstone extends LogicMain implements ILogic {

    public LogicRedstone() {
        super();
    }

    @Override
    public void work() {}

    @Override
    public boolean isPowered() {
        return world.isBlockIndirectlyGettingPowered(this.x, this.y, this.z);
    }

    @Override
    public String getName() {
        return "Logic Redstone";
    }

}