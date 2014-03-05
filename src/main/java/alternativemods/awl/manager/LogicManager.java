package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.logic.LogicMain;
import alternativemods.awl.logic.LogicRedstone;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:32
 */
public class LogicManager {

    private LogicMain activeLogic;

    public LogicManager() {
        this.activeLogic = new LogicRedstone();
    }

    public LogicMain getActiveLogic() {
        return this.activeLogic;
    }

    public void setNextLogic() {
        this.activeLogic = (LogicMain) Main.logicRegister.getNextLogic(this.activeLogic);
        Main.proxy.addClientChat("Active logic set to \"" + this.activeLogic.getName() + "\"!");
    }

}