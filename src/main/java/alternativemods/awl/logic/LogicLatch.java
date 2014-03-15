package alternativemods.awl.logic;

import alternativemods.awl.api.logic.ILogic;

/**
 * Author: Lordmau5
 * Date: 06.03.14
 * Time: 15:14
 */
public class LogicLatch extends ILogic {

    @Override
    public void work(boolean powered) {
        if(powered)
            setPowered(!this.isPowered());
    }

    @Override
    public String getName() {
        return "Logic Latch";
    }
}