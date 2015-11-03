package com.gome.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.gome.netty.codec.MarshallingCodecFactory;
import com.gome.netty.config.Config;
import com.gome.netty.handlers.ConnectAuthHandler;
import com.gome.netty.handlers.HeartBeatRequestHandler;

public class GomeCloudNettyClient {
	
	private ScheduledExecutorService executor = Executors
		    .newScheduledThreadPool(1);
	    EventLoopGroup group = new NioEventLoopGroup();

	    public void connect(int port, String host) throws Exception {

		// 配置客户端NIO线程组

		try {
		    Bootstrap b = new Bootstrap();
		    b.group(group).channel(NioSocketChannel.class)
			    .option(ChannelOption.TCP_NODELAY, true)
			    .handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch)
					throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast("decoder", MarshallingCodecFactory.buildMarshallingDecoder());
				    p.addLast("encoder", MarshallingCodecFactory.buildMarshallingEncoder());
				    p.addLast("checkAuthHandler", new ConnectAuthHandler());
				    p.addLast("heartBeatRequest", new HeartBeatRequestHandler());
				}
			    });
		    // 发起异步连接操作
		    
		    ChannelFuture future = b.connect(new InetSocketAddress(host, port)).sync();
		    future.channel().closeFuture().sync();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
System.out.println("in finally");
		    // 所有资源释放完成之后，清空资源，再次发起重连操作
		    executor.execute(new Runnable() {
			@Override
			public void run() {
			    try {
				TimeUnit.SECONDS.sleep(1);
				try {
				    connect(Config.getPort(), Config.getIp());// 发起重连操作
				} catch (Exception e) {
				    e.printStackTrace();
				}
			    } catch (InterruptedException e) {
				e.printStackTrace();
			    }
			}
		    });
		}
	    }

	    /**
	     * @param args
	     * @throws Exception
	     */
	    public static void main(String[] args) throws Exception {
	    	System.out.println(Config.getPort());
	    	System.out.println(Config.getIp());
	    	System.out.println(Config.getMd5Code());
	    	new GomeCloudNettyClient().connect(Config.getPort(), Config.getIp());
	    }

}
