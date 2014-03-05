package alternativemods.awl.register;

import alternativemods.awl.logic.LogicAnd;
import alternativemods.awl.logic.LogicInverter;
import alternativemods.awl.api.logic.LogicMain;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:22
 */
public class LogicRegister {

    private List<LogicMain> logics = Lists.newArrayList();

    public LogicRegister() {
        register(new LogicInverter());
        register(new LogicAnd());
    }

    public void register(LogicMain logic) {
        this.logics.add(logic);
    }

    public LogicMain getLogicFromName(String name){
        for(LogicMain logic : this.logics){
            if(logic.getName().equals(name)){
                return logic;
            }
        }
        return null;
    }

    public LogicMain getNextLogic(LogicMain currentLogic){
        for(int i=0; i<this.logics.size(); i++) {
            LogicMain logic = this.logics.get(i);
            if(logic.getName().equals(currentLogic.getName())) {
                if(i + 1 >= this.logics.size())
                    return this.logics.get(0);
                return this.logics.get(i + 1);
            }
        }
        return this.logics.get(0);
    }
}