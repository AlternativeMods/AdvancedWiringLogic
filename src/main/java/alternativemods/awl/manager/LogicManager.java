package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.api.logic.ILogic;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:32
 */
public class LogicManager {

    private ILogic activeLogic;

    public LogicManager() {

    }

    public ILogic getActiveLogic() {
        return this.activeLogic;
    }

    public void setNextLogic() {
        this.activeLogic = Main.logicRegister.getNextLogic(this.activeLogic);
        Main.proxy.addClientChat("Active logic set to \"" + this.activeLogic.getName() + "\"!");
    }

}