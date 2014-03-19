package alternativemods.awl.manager;

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
@SuppressWarnings("unused")
public class WiresContainer {

    public List<Wire> wires = Lists.newArrayList();

    public void updateWirePoints(Wire wire){
        World world = MinecraftServer.getServer().worldServerForDimension(wire.dimension);
        AbstractPoint startPt = wire.points.get(0);
        AbstractPoint endPt = wire.points.get(wire.points.size() - 1);

        world.notifyBlockOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));

        world.notifyBlocksOfNeighborChange(startPt.x, startPt.y, startPt.z, world.getBlock(startPt.x, startPt.y, startPt.z));
        world.notifyBlocksOfNeighborChange(endPt.x, endPt.y, endPt.z, world.getBlock(endPt.x, endPt.y, endPt.z));
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

    public void removeWires(AbstractPoint startPoint) {
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

    public void removeLogic(AbstractLogic logic) {
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
        for(Wire wire : this.wires){
        	if(world.provider.dimensionId != wire.dimension)
        		continue;
        	
        	AbstractPoint endPt = wire.points.get(0);
            if(point.equals(endPt))
                return true;
            for(ForgeDirection dr : ForgeDirection.VALID_DIRECTIONS)
            	if(point.equals(new Point(endPt.x - dr.offsetX, endPt.y - dr.offsetY, endPt.z - dr.offsetZ)))
            		return true;
        }
        return false;
    }

    public boolean isWireEndingAt(World world, AbstractPoint point) {
        for(Wire wire : this.wires){
        	if(world.provider.dimensionId != wire.dimension)
        		continue;
        	
        	AbstractPoint endPt = wire.points.get(wire.points.size() - 1);
            if(point.equals(endPt))
                return true;
            for(ForgeDirection dr : ForgeDirection.VALID_DIRECTIONS)
            	if(point.equals(new Point(endPt.x - dr.offsetX, endPt.y - dr.offsetY, endPt.z - dr.offsetZ)))
            		return true;
        }
        return false;
    }

    public void updatePoweredState(boolean signal, Wire wire) {
        wire.setPowered(signal);
    }

    public void updatePoweredState(AbstractLogic logic, Wire wire) {
        updatePoweredState(logic.isPowered(), wire);
    }

    public void work(World world, AbstractPoint point) {
        for(Wire wire : this.wires)
            if(wire.points.get(0).equals(point)){
                boolean signal = world.getBlockPowerInput(point.x, point.y, point.z) > 0;
                updatePoweredState(signal, wire);
                updateWirePoints(wire);
            }
    }

    public boolean isBlockPowered(World world, AbstractPoint point) {
        for(Wire wire : this.wires)
    		if(wire.isPowered()){
                AbstractPoint checkPt = wire.points.get(wire.points.size() - 1);
                if(point.equals(checkPt))
                    return true;
                for(ForgeDirection dr : ForgeDirection.VALID_DIRECTIONS){
                    AbstractPoint drPoint = new Point(checkPt.x + dr.offsetX, checkPt.y + dr.offsetY, checkPt.z + dr.offsetZ);
                    if(point.equals(drPoint))
                        return true;
                }
            }

    	return false;
    }

}
