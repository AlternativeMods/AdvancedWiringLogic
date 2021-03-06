package alternativemods.awl.network;

import alternativemods.awl.network.handlers.server.AddLogic;
import alternativemods.awl.network.handlers.server.AddWire;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;

import java.util.EnumMap;

/**
 * Author: Lordmau5
 * Date: 21.02.14
 * Time: 12:54
 */
public class NetworkHandler {

    private static EnumMap<Side, FMLEmbeddedChannel> channels;

    public static void registerChannels(Side side){
        channels = NetworkRegistry.INSTANCE.newChannel("AWL", new PacketCodec());

        ChannelPipeline pipeline = channels.get(Side.SERVER).pipeline();
        String targetName = channels.get(Side.SERVER).findChannelHandlerNameForType(PacketCodec.class);

        pipeline.addAfter(targetName, "AddWire", new AddWire());
        pipeline.addAfter(targetName, "AddLogic", new AddLogic());

        if(side.isClient()){
            registerClientHandlers();
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerClientHandlers(){
        ChannelPipeline pipeline = channels.get(Side.CLIENT).pipeline();
        String targetName = channels.get(Side.CLIENT).findChannelHandlerNameForType(PacketCodec.class);
    }

    public static Packet getProxyPacket(AWLPacket packet){
        return channels.get(FMLCommonHandler.instance().getEffectiveSide()).generatePacketFrom(packet);
    }

    public static void sendPacketToPlayer(AWLPacket packet, EntityPlayer player){
        FMLEmbeddedChannel channel = channels.get(Side.SERVER);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channel.writeOutbound(packet);
    }

    public static void sendPacketToServer(AWLPacket packet){
        FMLEmbeddedChannel channel = channels.get(Side.CLIENT);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channel.writeOutbound(packet);
    }

    public static EntityPlayerMP getPlayer(ChannelHandlerContext ctx){
        return ((NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER).get()).playerEntity;
    }
}
