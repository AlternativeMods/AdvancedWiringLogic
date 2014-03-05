package alternativemods.awl.register;

import alternativemods.awl.logic.ILogic;
import alternativemods.awl.logic.LogicMain;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 17:22
 */
public class LogicRegister {

    private List<ILogic> logics = Lists.newArrayList();

    public void register(LogicMain logic){
        this.logics.add(logic);
    }

    public ILogic getLogicFromName(String name){
        for(ILogic logic : this.logics){
            if(logic.getName().equals(name)){
                return logic;
            }
        }
        return null;
    }

    public ILogic getNextLogic(ILogic currentLogic){
        Iterator<ILogic> it = this.logics.iterator();
        while(it.hasNext()){
            ILogic logic = it.next();
            if(logic == currentLogic){
                if(it.hasNext()){
                    return it.next();
                }else{
                    return this.logics.get(0);
                }
            }
        }
        return this.logics.get(0);
    }
}