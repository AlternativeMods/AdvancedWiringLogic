package alternativemods.awl.register;

import alternativemods.awl.api.logic.AbstractLogic;
import alternativemods.awl.logic.LogicAnd;
import alternativemods.awl.logic.LogicInverter;
import alternativemods.awl.logic.LogicLatch;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:22
 */
public class LogicRegister {

    private List<AbstractLogic> logics = Lists.newArrayList();

    public LogicRegister() {
        register(new LogicInverter());
        register(new LogicAnd());
        register(new LogicLatch());
    }

    public void register(AbstractLogic logic) {
        this.logics.add(logic);
    }

    public AbstractLogic getLogicFromName(String name) {
        for(AbstractLogic logic : this.logics){
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

    public AbstractLogic getNextLogic(AbstractLogic currentLogic){
        if(currentLogic == null)
            return this.logics.get(0);
        for(int i=0; i<this.logics.size(); i++) {
            AbstractLogic logic = this.logics.get(i);
            if(logic.getName().equals(currentLogic.getName())) {
                if(i + 1 >= this.logics.size())
                    return this.logics.get(0);
                return this.logics.get(i + 1);
            }
        }
        return this.logics.get(0);
    }
}