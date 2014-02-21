package alternativemods.awl.network.handlers.server;

import alternativemods.awl.Main;
import alternativemods.awl.network.AWLPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Author: Lordmau5
 * Date: 21.02.14
 * Time: 12:58
 */
public class AddLogic extends SimpleChannelInboundHandler<AWLPacket.Server.AddLogic> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AWLPacket.Server.AddLogic msg) throws Exception{
        Main.logicContainer.addLogic(msg.logic);
    }

}