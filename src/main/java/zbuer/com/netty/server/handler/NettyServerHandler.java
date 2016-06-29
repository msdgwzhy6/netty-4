package zbuer.com.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import zbuer.com.netty.message.BaseMsg;
import zbuer.com.netty.message.MsgType;
import zbuer.com.netty.server.NettyChannelMap;

/**
 * @author buer
 * @since 16/6/28
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<BaseMsg> {

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		NettyChannelMap.remove((SocketChannel) ctx.channel());
	}

	@Override
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg)
			throws Exception {
		if(MsgType.LOGIN.equals(baseMsg.getType())){
			//登录逻辑

		}


	}
}
