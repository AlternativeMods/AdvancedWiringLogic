package alternativemods.awl.register;

import alternativemods.awl.api.logic.ILogic;
import alternativemods.awl.logic.LogicAnd;
import alternativemods.awl.logic.LogicInverter;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:22
 */
public class LogicRegister {

    private List<ILogic> logics = Lists.newArrayList();

    public LogicRegister() {
        register(new LogicInverter());
        register(new LogicAnd());
    }

    public void register(ILogic logic) {
        this.logics.add(logic);
    }

    public ILogic getLogicFromName(String name) {
        for(ILogic logic : this.logics){
            if(logic.getName().equals(name)){
                try {
                    return logic.getClass().newInstance();
                }
                catch (InstantiationException e) {}
                catch (IllegalAccessException e) {}
            }
        }
        return null;
    }

    public ILogic getNextLogic(ILogic currentLogic){
        if(currentLogic == null)
            return this.logics.get(0);
        for(int i=0; i<this.logics.size(); i++) {
            ILogic logic = this.logics.get(i);
            if(logic.getName().equals(currentLogic.getName())) {
                if(i + 1 >= this.logics.size())
                    return this.logics.get(0);
                return this.logics.get(i + 1);
            }
        }
        return this.logics.get(0);
    }
}