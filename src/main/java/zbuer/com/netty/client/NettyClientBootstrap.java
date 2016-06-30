package zbuer.com.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import zbuer.com.netty.client.handler.NettyClientHandler;
import zbuer.com.netty.message.LoginMsg;

import java.util.concurrent.TimeUnit;

/**
 * @author buer
 * @since 16/6/28
 */
public class NettyClientBootstrap {

	private int port;

	private String host;
	private SocketChannel socketChannel;
	private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

	public NettyClientBootstrap(int port, String host) {
		this.port = port;
		this.host = host;
		start();
	}

	private void start() {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.group(eventLoopGroup);
		bootstrap.remoteAddress(host, port);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new IdleStateHandler(20,10,0));
				ch.pipeline().addLast(new ObjectEncoder());
				ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
				ch.pipeline().addLast(new NettyClientHandler());

			}
		});
		try {
			ChannelFuture future = bootstrap.connect(host,port).sync();
			if(future.isSuccess()){
				socketChannel = (SocketChannel)future.channel();
;				System.out.println("connect server success .....");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception{
		NettyClientBootstrap clientBootstrap = new NettyClientBootstrap(9999,"127.0.0.1");
		LoginMsg loginMsg = new LoginMsg();
		loginMsg.setUserName("buer");
		loginMsg.setPassword("buer");
		clientBootstrap.socketChannel.writeAndFlush(loginMsg);
		while (true){
			TimeUnit.SECONDS.sleep(15);
		}
	}

}
