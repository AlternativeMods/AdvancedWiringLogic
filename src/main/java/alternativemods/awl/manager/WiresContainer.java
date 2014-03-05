package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.api.logic.LogicMain;
import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

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
        this.wires = new ArrayList<Wire>();
    }

    public void updateWirePoints(Wire wire) {
        World world = MinecraftServer.getServer().worldServerForDimension(wire.dimension);
        Point startPt = wire.points.get(0);
        Point endPt = wire.points.get(wire.points.size() - 1);
        world.notifyBlocksOfNeighborChange(startPt.x, startPt.y, startPt.z, world.getBlock(startPt.x, startPt.y, startPt.z));
        world.notifyBlocksOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
        world.notifyBlockOfNeighborChange(startPt.x, startPt.y, startPt.z, world.getBlock(startPt.x, startPt.y, startPt.z));
        world.notifyBlockOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
        world.markBlockForUpdate(startPt.x, startPt.y, startPt.z);
        world.markBlockForUpdate(endPt.x, endPt.y, endPt.z);
    }

    public void addWire(Wire wire) {
        this.wires.add(wire);
        updateWirePoints(wire);
    }

    public void removeWires(Point startPoint) {
        List<Wire> toRemove = new ArrayList<Wire>();
        for(Wire wire : this.wires) {
            if(wire.points.get(0).equals(startPoint)) {
                toRemove.add(wire);
            }
        }
        for(Wire wire : toRemove) {
            this.wires.remove(wire);
            updateWirePoints(wire);
        }
    }

    public void shortenWires(World world, Point endPoint) {
        List<Wire> toShorten = new ArrayList<Wire>();
        for(Wire wire : this.wires) {
            if(wire.points.get(wire.points.size() - 1).equals(endPoint)) {
                toShorten.add(wire);
            }
        }
        for(Wire wire : toShorten) {
            Wire oldWire = wire;
            wire.points.remove(wire.points.get(wire.points.size() - 1));
            if(wire.points.size() == 1) {
                this.wires.remove(wire);
                updateWirePoints(oldWire);
            }
            else {
                Point nextPossible = wire.points.get(wire.points.size() - 1);
                if(world.isAirBlock(nextPossible.x, nextPossible.y, nextPossible.z) || !world.getBlock(nextPossible.x, nextPossible.y, nextPossible.z).isOpaqueCube()) {
                    shortenWires(world, nextPossible);
                }
            }
        }
    }

    public void removeWire(Wire wire) {
        this.wires.remove(wire);
        updateWirePoints(wire);
    }

    public boolean isWireStartingAt(World world, Point point) {
        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(0).equals(point))
                return true;
        return false;
    }

    public boolean isWireEndingAt(World world, Point point) {
        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(wire.points.size() - 1).equals(point))
                return true;
        return false;
    }

    public void notifyWireEnds(World world, Point point) {
        if(world.isRemote)
            return;

        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(0).equals(point)) {
                Point endPt = wire.points.get(wire.points.size() - 1);
                world.notifyBlocksOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
                world.notifyBlockOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
                world.markBlockForUpdate(endPt.x, endPt.y, endPt.z);
            }

        for(LogicMain logic : Main.logicContainer.logics)
            if(logic.positionEquals(point, world.provider.dimensionId))
                logic.work();
    }

    public boolean isBlockPoweredByWire(World world, int x, int y, int z, int dimension) {
        if(world.isRemote)
            return false;

        if(this.wires.isEmpty())
            return false;

        for(Wire wire : this.wires) {
            if(wire.dimension == dimension) {
                Point stPoint = wire.points.get(0);
                if(world.getBlockPowerInput(stPoint.x, stPoint.y, stPoint.z) > 0)
                    return true;
            }
        }

        return false;
    }

}