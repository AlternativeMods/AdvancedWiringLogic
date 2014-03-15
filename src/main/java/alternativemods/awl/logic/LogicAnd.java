package alternativemods.awl.logic;

import alternativemods.awl.api.logic.AbstractLogic;
import alternativemods.awl.util.Wire;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Author: Lordmau5
 * Date: 05.03.14
 * Time: 20:24
 */
public class LogicAnd extends AbstractLogic {

	private List<Wire> inputs = Lists.newArrayList();
	
	public void addWire(Wire wire) {
		if(canAddLogic()){
            inputs.add(wire);
        }
	}
	
	@Override
	public boolean setupWith(Wire wire) {
		if(!canAddLogic()){
            return false;
        }
		addWire(wire);
		return true;
	}
	
	@Override
	public boolean canAddLogic() {
		return inputs.size() < 2;
	}
	
	@Override
	public String getAddError() {
		return "ALREADY GOT 2 WIRES!";
	}
	
	public int getWireSize() {
		return inputs.size();
	}
	
    @Override
    public void work(boolean powered) {
        setPowered(false);
        if(inputs.size() < 2)
        	return;

        if(inputs.get(0).isPowered() && inputs.get(1).isPowered())
            setPowered(true);
    }

    @Override
    public String getName() {
        return "Logic And";
    }
}