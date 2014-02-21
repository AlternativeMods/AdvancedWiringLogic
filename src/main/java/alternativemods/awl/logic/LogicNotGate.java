package alternativemods.awl.logic;

/**
 * Author: Lordmau5
 * Date: 21.02.14
 * Time: 17:19
 */
public class LogicNotGate extends LogicMain {

    @Override
    public boolean isPowered() {
        return !world.isBlockIndirectlyGettingPowered(this.x, this.y, this.z);
    }

    @Override
    public String getName() {
        return "Logic Not Gate";
    }
}