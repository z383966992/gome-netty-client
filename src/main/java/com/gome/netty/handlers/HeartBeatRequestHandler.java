package com.gome.netty.handlers;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.gome.netty.struct.Message;
import com.gome.netty.struct.MessageType;
/**
 * 客户端发送心跳请求
 * @author zhouliangliang
 *
 */
public class HeartBeatRequestHandler extends ChannelHandlerAdapter{

	/**
	 * 权限认证成功之后，主动发起心跳
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message message = (Message)msg;		
		if(message != null &&
				message.getMessageType() == MessageType.CONNECT_AUTH_RESPONSE.value()) {
			ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
			
		} else if (message != null 
				&& message.getMessageType() == MessageType.HEART_BEAT_RESPONSE.value()) {
System.out.println("client receive server heart beat response");			
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
System.out.println(cause.getMessage());		
	}
}

class HeartBeatTask implements Runnable {
    ChannelHandlerContext ctx;
    
    public HeartBeatTask(ChannelHandlerContext ctx) {
    	this.ctx = ctx;
	}
    
	@Override
	public void run() {
		Message message = new Message();
		message.setMessageType(MessageType.HEART_BEAT_REQUEST.value());
		ctx.writeAndFlush(message);
	}
}
