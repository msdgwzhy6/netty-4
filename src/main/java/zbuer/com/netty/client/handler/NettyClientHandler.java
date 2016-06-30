package zbuer.com.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import zbuer.com.netty.message.*;

import java.util.Date;

/**
 * @author buer
 * @since 16/6/28
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg> {

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, BaseMsg msg) throws Exception {

		MsgType type = msg.getType();
		switch (type) {
		case LOGIN:{
			LoginMsg loginMsg = new LoginMsg();
			loginMsg.setUserName("buer");
			loginMsg.setPassword("buer");
			ctx.writeAndFlush(loginMsg);
		}
		break;
		case PING:{
			System.out.println("receive ping from server------->" + new Date());
		}
		break;
		case ASK:{
			ReplyClientBody clientBody = new ReplyClientBody("reply client body");
			ReplyMsg replyMsg = new ReplyMsg();
			replyMsg.setReplyBody(clientBody);
			ctx.writeAndFlush(replyMsg);

		}
		break;
		case REPLY:{
			ReplyMsg replyMsg = (ReplyMsg)msg;
			ReplyServerBody replyServerBody = (ReplyServerBody)replyMsg.getReplyBody();
			System.out.println("receive client msg : " + replyServerBody);
		}
		break;
		case LOGIN_REPLY:{
			LoginReplyMsg loginReplyMsg = (LoginReplyMsg)msg;
			Constants.setClientId(loginReplyMsg.getLoginToken());
		}
		break;
		default:
			break;
		}
	}

	/**
	 * 利用空闲时间发送心跳
	 *
	 * @param ctx
	 * @param evt
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			switch (e.state()) {
			case WRITER_IDLE: {
				PingMsg pingMsg = new PingMsg();
				ctx.writeAndFlush(pingMsg);
				System.out.println("send ping to server for idle--- " + new Date());

			}
			default:
				break;
			}
		}
		super.userEventTriggered(ctx, evt);
	}
}
