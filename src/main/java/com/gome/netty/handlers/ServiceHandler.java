package com.gome.netty.handlers;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.gome.netty.struct.Message;
import com.gome.netty.struct.MessageType;

/**
 * 类说明.
 * 这个是业务handler,控制手机
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月30日    周亮亮    新建
 * </pre>
 */
public class ServiceHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) 
			throws Exception {
		
		Message message = (Message) msg;
		if (message != null && message.getContent() != null
				&& message.getMessageType() == MessageType.CONTROL_COMMAND.value()) {
			
			System.out.println("receive control command : " + message.getContent());
			System.out.println("control success!");
		}
	}
}
