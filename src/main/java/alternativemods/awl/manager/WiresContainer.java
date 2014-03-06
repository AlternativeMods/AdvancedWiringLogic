package alternativemods.awl.manager;

import alternativemods.awl.api.logic.ILogic;
import alternativemods.awl.api.util.IPoint;
import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;
import com.google.common.collect.Lists;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
        IPoint startPt = wire.points.get(0);
        IPoint endPt = wire.points.get(wire.points.size() - 1);
        world.notifyBlocksOfNeighborChange(startPt.x, startPt.y, startPt.z, world.getBlock(startPt.x, startPt.y, startPt.z));
        world.notifyBlocksOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
        world.notifyBlockOfNeighborChange(startPt.x, startPt.y, startPt.z, world.getBlock(startPt.x, startPt.y, startPt.z));
        world.notifyBlockOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
        world.markBlockForUpdate(startPt.x, startPt.y, startPt.z);
        world.markBlockForUpdate(endPt.x, endPt.y, endPt.z);
        notifyWireEnds(world, endPt);
    }

    public void addWire(Wire wire) {
        if(!this.wires.contains(wire))
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

    public void removeLogic(World world, ILogic logic) {
        List<Wire> wrRemove = Lists.newArrayList();
        for(Wire wire : this.wires) {
            List<IPoint> toRemove = Lists.newArrayList();
            for(IPoint point : wire.points) {
                if(point instanceof ILogic && ((ILogic) point).positionEquals(logic.x, logic.y, logic.z, logic.dimension))
                    toRemove.add(point);
            }
            wire.points.removeAll(toRemove);
            if(wire.points.size() <= 1)
                wrRemove.add(wire);
            else
                updateWirePoints(wire);
        }
        this.wires.removeAll(wrRemove);
    }

    public void shortenWires(World world, IPoint endPoint) {
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
                IPoint nextPossible = wire.points.get(wire.points.size() - 1);
                if(world.isAirBlock(nextPossible.x, nextPossible.y, nextPossible.z) || !world.getBlock(nextPossible.x, nextPossible.y, nextPossible.z).isOpaqueCube()
                || nextPossible instanceof ILogic) {
                    shortenWires(world, nextPossible);
                }
            }
        }
    }

    public void removeWire(Wire wire) {
        this.wires.remove(wire);
        updateWirePoints(wire);
    }

    public boolean isWireStartingAt(World world, IPoint point) {
        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(0).equals(point))
                return true;
        return false;
    }

    public boolean isWireEndingAt(World world, IPoint point) {
        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(wire.points.size() - 1).equals(point))
                return true;
        return false;
    }

    public void notifyWireEnds(World world, IPoint point) {
        if(world.isRemote)
            return;

        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(0).equals(point)) {
                IPoint endPt = wire.points.get(wire.points.size() - 1);
                world.notifyBlocksOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
                world.notifyBlockOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
                world.markBlockForUpdate(endPt.x, endPt.y, endPt.z);
            }
    }

    public boolean isBlockPoweredByWire(World world, int x, int y, int z, int dimension) {
        if(world.isRemote)
            return false;

        if(this.wires.isEmpty())
            return false;

        boolean signal = false;

        for(Wire wire : this.wires) {
            if(wire.dimension == dimension) {
                IPoint endPt = wire.points.get(wire.points.size() - 1);
                boolean found = false;
                for(ForgeDirection dr : ForgeDirection.VALID_DIRECTIONS) {
                    if(endPt.x + dr.offsetX == x && endPt.y + dr.offsetY == y && endPt.z + dr.offsetZ == z)
                        found = true;
                }
                if(endPt.x == x && endPt.y == y && endPt.z == z)
                    found = true;

                if(!found)
                    continue;

                IPoint stPoint = wire.points.get(0);
                signal = world.getBlockPowerInput(stPoint.x, stPoint.y, stPoint.z) > 0;
                for(int i=1; i<wire.points.size(); i++) {
                    if(wire.points.get(i) instanceof ILogic) {
                        ILogic logic = (ILogic) wire.points.get(i);
                        signal = logic.work(signal);
                    }
                }
                break;
            }
        }

        return signal;
    }

}