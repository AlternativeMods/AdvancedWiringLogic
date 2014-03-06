package alternativemods.awl.logic;

import alternativemods.awl.api.logic.ILogic;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 20:24
 */
public class LogicAnd extends ILogic {

    @Override
    public boolean work(boolean startingSignal, boolean isPowered) {
        return isPowered;
    }

    @Override
    public String getName() {
        return "Logic And";
    }
}