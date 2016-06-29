package zbuer.com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import zbuer.com.netty.server.handler.NettyServerHandler;

/**
 * @author buer
 * @since 16/6/28
 */
public class NettyServerBootstrap {

	private int port;

	private SocketChannel socketChannel;

	public NettyServerBootstrap(int port) {
		this.port = port;
		bind();
	}

	private void bind() {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup work = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, work);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		//通过noDelay 禁用Nagle,使消息立即发出去，不用等待到一定的数据量发出去
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		//保存长连接状态
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new ObjectEncoder());
				pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
				pipeline.addLast(new NettyServerHandler());

			}
		});
		ChannelFuture future = null;
		try {
			future = bootstrap.bind(port).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(future.isSuccess()){
			System.out.println("server start ------------->†");
		}




	}
}
