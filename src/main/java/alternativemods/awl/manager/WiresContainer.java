package alternativemods.awl.manager;

import alternativemods.awl.util.Wire;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 18:49
 */
public class WiresContainer {

    public List<Wire> wires;

    public WiresContainer() {
        wires = new ArrayList<Wire>();
    }

    public void addWire(Wire wire) {
        wires.add(wire);
    }

    public void removeWire(Wire wire) {
        wires.remove(wire);
    }

}