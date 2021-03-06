package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.api.logic.AbstractLogic;
import alternativemods.awl.api.util.AbstractPoint;
import alternativemods.awl.network.AWLPacket;
import alternativemods.awl.network.NetworkHandler;
import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 19.02.14
 * Time: 18:48
 */
public class WireManager {

    public boolean doingWire;
    public int dimension = 0;
    public List<AbstractPoint> points;

    public WireManager() {
        this.doingWire = false;
        this.points = null;
    }

    public void startWire(int x, int y, int z, int dimension) {
        if(this.doingWire)
            return;

        this.dimension = dimension;

        this.doingWire = true;
        this.points = new ArrayList<AbstractPoint>();

        if(Main.logicContainer.isLogicAtPos(x, y, z, dimension))
            this.points.add(Main.logicContainer.getLogicFromPosition(x, y, z, dimension));
        else
            this.points.add(new Point(x, y, z));
        Main.proxy.addClientChat("Starting a new wire!");
    }

    private boolean pointExists(AbstractPoint point) {
        for(AbstractPoint pt : this.points)
            if(pt.equals(point))
                return true;

        return false;
    }

    public void abortCreation() {
        this.doingWire = false;
        Main.proxy.addClientChat("Aborted wire-creation through dimension-change!");
    }

    public void addPoint(int x, int y, int z) {
        AbstractPoint point = new Point(x, y, z);
        if(pointExists(point))
            return;

        this.points.add(point);
        Main.proxy.addClientChat("Added point to wire! - Got " + this.points.size() + " points now!");
    }

    public void addLogic(AbstractLogic logic) {
        if(!this.doingWire)
            return;
        
        if(!logic.canAddLogic()) {
        	Main.proxy.addClientChat("Can't add logic \"" + logic.getName() + "\":");
        	Main.proxy.addClientChat(logic.getAddError());
        	return;
        }

        this.points.add(logic);
        Main.proxy.addClientChat("Added a \"" + logic.getName() + "\"! - Got " + this.points.size() + " points now!");
    }

    public void endWire() {
        if(!this.doingWire)
            return;

        this.doingWire = false;
        if(this.points.size() < 2) {
            Main.proxy.addClientChat("Only got one point - Aborting wire-creation!");
            this.points = null;
            return;
        }
        World world = Minecraft.getMinecraft().theWorld;
        AbstractPoint stPoint = this.points.get(0);
        AbstractPoint endPoint = this.points.get(this.points.size() - 1);
        if(world.isAirBlock(stPoint.x, stPoint.y, stPoint.z) || !world.getBlock(stPoint.x, stPoint.y, stPoint.z).isOpaqueCube() ||
           world.isAirBlock(endPoint.x, endPoint.y, endPoint.z) || !world.getBlock(endPoint.x, endPoint.y, endPoint.z).isOpaqueCube()) {
            Main.proxy.addClientChat("Start or end point is corrupted - Aborting wire-creation!");
            this.points = null;
            return;
        }

        NetworkHandler.sendPacketToServer(new AWLPacket.Server.AddWire(new Wire(this.points, this.dimension)));
        Main.proxy.addClientChat("Finished the wire with " + this.points.size() + " points!");
        this.points = null;
    }

}