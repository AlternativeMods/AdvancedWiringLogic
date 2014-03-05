package alternativemods.awl.register;

import alternativemods.awl.logic.ILogic;
import alternativemods.awl.logic.LogicMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:22
 */
public class LogicRegister {

    private List<ILogic> logics = new ArrayList<ILogic>();

    public void registerLogic(LogicMain logicClass) {
        this.logics.add(logicClass);
    }

    public ILogic convertLogic(String name) {
        for(ILogic logic : this.logics)
            if(logic.getName().equals(name))
                return logic;
        return null;
    }

    public ILogic getNextLogic(LogicMain currentLogic) {
        for(int i=0; i<this.logics.size(); i++) {
            if(this.logics.get(i).getName().equals(currentLogic.getName())) {
                if(i + 1 >= this.logics.size())
                    return this.logics.get(0);
                return this.logics.get(i + 1);
            }
        }
        return this.logics.get(0);
    }

}