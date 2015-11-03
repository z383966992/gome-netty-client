package com.gome.netty.struct;
/**
 * 消息类型
 * 类说明.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月28日    周亮亮    新建
 * </pre>
 */
public enum MessageType {
	
	HEART_BEAT_REQUEST(1,"心跳请求"),
	HEART_BEAT_RESPONSE(2,"心跳应答"),
	SERVICE_REQUEST(3, "服务请求"),
	SERVICE_RESPONSE(4, "服务应答"),
	MESSAGE_CHECK_SUCCESS(5, "MD5校验成功"),
	MESSAGE_CHECK_FAIL(6, "MD5校验失败"),
	CONNECT_AUTH_REQUEST(7, "连接请求"),
	CONNECT_AUTH_RESPONSE(8, "连接应答"),
	CONTROL_COMMAND(9, "控制命令");
	private int value;
	private String desc;
	
	private MessageType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int value() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String desc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
