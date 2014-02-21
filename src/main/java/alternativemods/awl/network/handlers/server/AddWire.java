package alternativemods.awl.network.handlers.server;

import alternativemods.awl.Main;
import alternativemods.awl.network.AWLPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Author: Lordmau5
 * Date: 21.02.14
 * Time: 12:56
 */
public class AddWire extends SimpleChannelInboundHandler<AWLPacket.Server.AddWire> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AWLPacket.Server.AddWire msg) throws Exception{
        Main.wiresContainer.addWire(msg.wire);
    }
}