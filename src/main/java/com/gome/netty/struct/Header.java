package com.gome.netty.struct;

public class Header {
	
	//消息类型
	private int messageType;
	//状态
	private int status;
	//用来检测有效性
	private String md5Check;
	
	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public String getMd5Check() {
		return md5Check;
	}

	public void setMd5Check(String md5Check) {
		this.md5Check = md5Check;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
