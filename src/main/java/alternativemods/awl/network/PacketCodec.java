package alternativemods.awl.network;

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Author: Lordmau5
 * Date: 21.02.14
 * Time: 12:55
 */
public class PacketCodec extends FMLIndexedMessageToMessageCodec<AWLPacket> {

    public PacketCodec() {
        this.addDiscriminator(0, AWLPacket.Server.AddWire.class);
        this.addDiscriminator(1, AWLPacket.Server.AddLogic.class);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, AWLPacket msg, ByteBuf target) throws Exception{
        msg.encode(target);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, AWLPacket msg){
        msg.decode(source);
    }
}