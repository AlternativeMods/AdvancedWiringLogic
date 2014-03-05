package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.logic.LogicInverter;
import alternativemods.awl.api.logic.LogicMain;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:32
 */
public class LogicManager {

    private LogicMain activeLogic;

    public LogicManager() {
        this.activeLogic = new LogicInverter();
    }

    public LogicMain getActiveLogic() {
        return this.activeLogic;
    }

    public void setNextLogic() {
        this.activeLogic = Main.logicRegister.getNextLogic(this.activeLogic);
        Main.proxy.addClientChat("Active logic set to \"" + this.activeLogic.getName() + "\"!");
    }

}