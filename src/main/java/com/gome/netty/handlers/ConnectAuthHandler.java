package com.gome.netty.handlers;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.gome.netty.config.Config;
import com.gome.netty.struct.Message;
import com.gome.netty.struct.MessageType;
import com.gome.netty.utils.Md5Util;

/**
 * 连接权限验证
 * 通过md5加密消息发送，服务端验证成功之后返回
 * @author zhouliangliang
 *
 */
public class ConnectAuthHandler extends ChannelHandlerAdapter{

	/**
	 * 通道建立起来之后，发送权限验证消息
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Message message = new Message();
		String content = "connnect_request";
		message.setContent(content);
		message.setMessageType(MessageType.CONNECT_AUTH_REQUEST.value());
		message.setMd5Check(Md5Util.getMd5Code(content + Config.getMd5Code()));
		//由手机端获得
		message.setChannelId("12345678");
System.out.println(Config.getMd5Code());		
		ctx.writeAndFlush(message);
	}
	
	/**
	 * 获得服务器端返回的权限认证成功或者失败消息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message message = (Message)msg;
		//如果是连接应答消息
		if(message != null 
				&& message.getMessageType() == MessageType.CONNECT_AUTH_RESPONSE.value()) {
			int status = message.getStatus();
			if(status == 1) {
System.out.println("连接校验成功，发送消息到下一个handler");				
				ctx.fireChannelRead(msg);
			} else {
System.out.println("连接校验失败，关闭连接");				
				ctx.close();
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println(cause.getMessage());
		ctx.close();
		
	}
}
