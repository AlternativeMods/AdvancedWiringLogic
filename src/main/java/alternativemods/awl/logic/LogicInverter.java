package alternativemods.awl.logic;

/**
 * Author: Lordmau5
 * Date: 21.02.14
 * Time: 17:19
 */
public class LogicInverter extends LogicMain implements ILogic {

    public LogicInverter() {
        super();
    }

    @Override
    public void work() {}

    @Override
    public boolean isPowered() {
        return !world.isBlockIndirectlyGettingPowered(this.x, this.y, this.z);
    }

    @Override
    public String getName() {
        return "Logic Inverter";
    }
}