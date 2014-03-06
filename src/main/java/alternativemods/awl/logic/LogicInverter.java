package alternativemods.awl.logic;

import alternativemods.awl.api.logic.ILogic;

/**
 * Author: Lordmau5
 * Date: 21.02.14
 * Time: 17:19
 */
public class LogicInverter extends ILogic {

    @Override
    public boolean work(boolean startingSignal, boolean isPowered) {
        return !isPowered;
    }

    @Override
    public String getName() {
        return "Logic Inverter";
    }
}