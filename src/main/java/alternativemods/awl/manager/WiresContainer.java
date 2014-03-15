package alternativemods.awl.manager;

import alternativemods.awl.Main;
import alternativemods.awl.api.logic.AbstractLogic;
import alternativemods.awl.api.util.AbstractPoint;
import alternativemods.awl.tiles.TileEntityLogic;
import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;
import com.google.common.collect.Lists;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
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

    public List<Wire> wires = Lists.newArrayList();

    public void updateWirePoints(Wire wire){
        World world = MinecraftServer.getServer().worldServerForDimension(wire.dimension);
        AbstractPoint startPt = wire.points.get(0);
        AbstractPoint endPt = wire.points.get(wire.points.size() - 1);
        world.notifyBlocksOfNeighborChange(startPt.x, startPt.y, startPt.z, world.getBlock(startPt.x, startPt.y, startPt.z));
        world.notifyBlocksOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
        world.notifyBlockOfNeighborChange(startPt.x, startPt.y, startPt.z, world.getBlock(startPt.x, startPt.y, startPt.z));
        world.notifyBlockOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
        world.markBlockForUpdate(startPt.x, startPt.y, startPt.z);
        world.markBlockForUpdate(endPt.x, endPt.y, endPt.z);
        notifyWireEnds(world, endPt);
    }

    public void addWire(Wire wire) {
    	World world = MinecraftServer.getServer().worldServerForDimension(wire.dimension);
        
    	for(AbstractPoint point : wire.points){
            if(point instanceof AbstractLogic) {
                TileEntity tmpTile = world.getTileEntity(point.x, point.y, point.z);
                if(tmpTile != null && tmpTile instanceof TileEntityLogic) {
                    AbstractLogic logic = ((TileEntityLogic)tmpTile).getLogic();
                    if(logic != null && !logic.setupWith(wire)){
                        return;
                    }
                }
            }
        }
        
        if(!this.wires.contains(wire)){
            this.wires.add(wire);
        }
        
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

    public void removeLogic(World world, AbstractLogic logic) {
        List<Wire> wrRemove = Lists.newArrayList();
        for(Wire wire : this.wires) {
            List<AbstractPoint> toRemove = Lists.newArrayList();
            for(AbstractPoint point : wire.points) {
                if(point instanceof AbstractLogic && ((AbstractLogic) point).positionEquals(logic.x, logic.y, logic.z, logic.dimension))
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

    public void shortenWires(World world, AbstractPoint endPoint) {
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
                AbstractPoint nextPossible = wire.points.get(wire.points.size() - 1);
                if(world.isAirBlock(nextPossible.x, nextPossible.y, nextPossible.z) || !world.getBlock(nextPossible.x, nextPossible.y, nextPossible.z).isOpaqueCube()
                || nextPossible instanceof AbstractLogic) {
                    shortenWires(world, nextPossible);
                }
            }
        }
    }

    public boolean isWireStartingAt(World world, AbstractPoint point) {
        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(0).equals(point))
                return true;
        return false;
    }

    public boolean isWireEndingAt(World world, AbstractPoint point) {
        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(wire.points.size() - 1).equals(point))
                return true;
        return false;
    }

    public void updatePoweredState(boolean signal, Wire wire) {
        wire.setPowered(signal);
    }

    public void updatePoweredState(AbstractLogic logic, Wire wire) {
        wire.setPowered(logic.isPowered());
    }

    public void notifyWireEnds(World world, AbstractPoint point) {
        if(world.isRemote)
            return;

        for(Wire wire : this.wires)
            if(world.provider.dimensionId == wire.dimension && wire.points.get(0).equals(point)) {
                boolean signal = world.getBlockPowerInput(point.x, point.y, point.z) > 0;

                AbstractPoint endPt = wire.points.get(wire.points.size() - 1);
                if(Main.logicContainer.isLogicAtPos(endPt, wire.dimension)) {
                    AbstractLogic logic = Main.logicContainer.getLogicFromPosition(endPt.x, endPt.y, endPt.z, wire.dimension);
                    logic.work(signal);
                    updatePoweredState(logic, wire);
                }
                else
                    updatePoweredState(signal, wire);

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
        
        for(Wire wire : this.wires) {
            if(wire.dimension == dimension) {
                if(!wire.isPowered())
                    continue;

                if(isWireEndingAt(world, new Point(x, y, z)))
                    return true;
                for(ForgeDirection dr : ForgeDirection.VALID_DIRECTIONS)
                    if(isWireEndingAt(world, new Point(x + dr.offsetX, y + dr.offsetY, z + dr.offsetZ)))
                        return true;
            }
        }

        return false;
    }

}
