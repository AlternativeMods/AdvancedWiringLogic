package alternativemods.awl.network;

import alternativemods.awl.Main;
import alternativemods.awl.api.logic.ILogic;
import alternativemods.awl.api.util.IPoint;
import alternativemods.awl.block.Blocks;
import alternativemods.awl.tiles.TileEntityLogic;
import alternativemods.awl.util.Point;
import alternativemods.awl.util.Wire;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 21.02.14
 * Time: 12:49
 */
public abstract class AWLPacket {
    public abstract void encode(ByteBuf buffer);

    public abstract void decode(ByteBuf buffer);

    public static abstract class Client {

    }

    public static class Server {
        public static class AddWire extends AWLPacket {
            public Wire wire;

            public AddWire() {}

            public AddWire(Wire wire) {
                this.wire = wire;
            }

            @Override
            public void encode(ByteBuf buffer){
                buffer.writeInt(this.wire.dimension);
                buffer.writeInt(this.wire.points.size());

                for(IPoint point : this.wire.points) {
                    if(Main.logicContainer.isLogicAtPos(point.x, point.y, point.z, this.wire.dimension))
                        buffer.writeBoolean(true);
                    else
                        buffer.writeBoolean(false);
                    buffer.writeInt(point.x);
                    buffer.writeInt(point.y);
                    buffer.writeInt(point.z);
                    if(Main.logicContainer.isLogicAtPos(point.x, point.y, point.z, this.wire.dimension)) {
                        ByteBufUtils.writeUTF8String(buffer, Main.logicContainer.getLogicFromPosition(point.x, point.y, point.z, this.wire.dimension).getName());
                    }
                }
            }

            @Override
            public void decode(ByteBuf buffer){
                int dimension = buffer.readInt();
                int ptLength = buffer.readInt();

                List<IPoint> points = new ArrayList<IPoint>();
                for(int i=0; i<ptLength; i++) {
                    boolean isLogic = buffer.readBoolean();
                    int x = buffer.readInt();
                    int y = buffer.readInt();
                    int z = buffer.readInt();
                    if(isLogic) {
                        ILogic logic = Main.logicRegister.getLogicFromName(ByteBufUtils.readUTF8String(buffer));
                        logic.setVars(MinecraftServer.getServer().worldServerForDimension(dimension), x, y, z, dimension);
                        points.add(logic);
                    }
                    else
                        points.add(new Point(x, y, z));
                }
                this.wire = new Wire(points, dimension);
            }
        }

        public static class AddLogic extends AWLPacket {
            public ILogic logic;

            public AddLogic() {}

            public AddLogic(ILogic logic) {
                this.logic = logic;
            }

            @Override
            public void encode(ByteBuf buffer){
                ByteBufUtils.writeUTF8String(buffer, this.logic.getName());
                int[] pos = this.logic.getPosition();
                for(Integer intx : pos)
                    buffer.writeInt(intx);
            }

            @Override
            public void decode(ByteBuf buffer){
                String logicName = ByteBufUtils.readUTF8String(buffer);
                int x = buffer.readInt();
                int y = buffer.readInt();
                int z = buffer.readInt();
                int dimension = buffer.readInt();

                World world = MinecraftServer.getServer().worldServerForDimension(dimension);
                world.setBlock(x, y, z, Blocks.blockLogic);

                System.out.println(logicName + " at " + x + ":" + y + ":" + z);

                this.logic = Main.logicRegister.getLogicFromName(logicName);
                this.logic.setVars(MinecraftServer.getServer().worldServerForDimension(dimension), x, y, z, dimension);

                world.setTileEntity(x, y, z, new TileEntityLogic(this.logic));
            }
        }
    }
}