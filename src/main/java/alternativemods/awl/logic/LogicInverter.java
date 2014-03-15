package alternativemods.awl.logic;

import alternativemods.awl.api.logic.AbstractLogic;

/**
 * Author: Lordmau5
 * Date: 21.02.14
 * Time: 17:19
 */
public class LogicInverter extends AbstractLogic {

    @Override
    public void work(boolean powered) {
        setPowered(!powered);
    }

    @Override
    public String getName() {
        return "Logic Inverter";
    }
}