package zbuer.com.netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;
import zbuer.com.netty.message.*;
import zbuer.com.netty.server.NettyChannelMap;

import java.util.Date;

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
		//登录逻辑

		if (MsgType.LOGIN.equals(baseMsg.getType())) {
			LoginMsg loginMsg = (LoginMsg) baseMsg;
			if ("buer".equals(loginMsg.getUserName()) && "buer".equals(loginMsg.getPassword())) {
				String loginToken = NettyChannelMap.add((SocketChannel) channelHandlerContext.channel());
				LoginReplyMsg loginReplyMsg = new LoginReplyMsg();
				loginReplyMsg.setSuccess(true);
				loginReplyMsg.setLoginToken(loginToken);
				channelHandlerContext.channel().writeAndFlush(loginReplyMsg);
			} else {
				System.out.println("client " + loginMsg.getUserName() + "登录失败");
				LoginReplyMsg loginReplyMsg = new LoginReplyMsg();
				loginReplyMsg.setSuccess(false);
				channelHandlerContext.channel().writeAndFlush(loginReplyMsg);
			}

		}else{
			String clientId = baseMsg.getClientId();
			if(clientId == null ){
				//说明未登录。或者链接断了，服务器向客户端发起登录请求，让客户端重新登录
				LoginMsg loginMsg = new LoginMsg();
				channelHandlerContext.channel().writeAndFlush(loginMsg);
				return;
			}
			Channel channel = NettyChannelMap.get(clientId);
			if(channel == null ){
				//说明未登录。或者链接断了，服务器向客户端发起登录请求，让客户端重新登录
				LoginMsg loginMsg = new LoginMsg();
				channelHandlerContext.channel().writeAndFlush(loginMsg);
				return;
			}
			Channel currentChannle = channelHandlerContext.channel();

			//登录id不匹配
			if(currentChannle != channel){
				LoginMsg loginMsg = new LoginMsg();

				channelHandlerContext.channel().writeAndFlush(loginMsg);
				return;
			}

		}

		switch (baseMsg.getType()) {

		case PING: {
			PingMsg pingMsg = (PingMsg) baseMsg;
			PingMsg replyPing = new PingMsg();
			System.out.println(
					"receive client ping : keepalive clientId = " + baseMsg.getClientId() + " " + new Date());
			NettyChannelMap.get(pingMsg.getClientId()).writeAndFlush(replyPing);
			break;

		}
		case ASK: {
			AskMsg askMsg = (AskMsg) baseMsg;
			if ("authToken".equals(askMsg.getParams().getAuth())) {
				ReplyServerBody replyServerBody = new ReplyServerBody("server info $$$$");
				ReplyMsg replyMsg = new ReplyMsg();
				replyMsg.setReplyBody(replyServerBody);
				NettyChannelMap.get(askMsg.getClientId()).writeAndFlush(replyMsg);

			}
			break;
		}
		case REPLY: {
			ReplyMsg replyMsg = (ReplyMsg) baseMsg;
			ReplyClientBody clientBody = (ReplyClientBody) replyMsg.getReplyBody();
			System.out.println("receive client msg " + clientBody.getClientInfo());
			break;
		}
		case LOGOUT: {
			NettyChannelMap.remove((SocketChannel) channelHandlerContext.channel());
			break;
		}
		default:
			break;

		}
		ReferenceCountUtil.release(baseMsg);

	}
}
