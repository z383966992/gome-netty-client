package com.gome.netty.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	
	private static String ip;
	private static int port;
	private static String md5Code;
	
	private Config() {}
	
	static {
		InputStream inputStream = Config.class.getClassLoader()
				.getResourceAsStream("config.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
			Config.ip = p.getProperty("com.gome.netty.server.ip");
			Config.port = Integer.valueOf(p.getProperty("com.gome.netty.server.port"));
			Config.md5Code = p.getProperty("com.gome.netty.server.md5");
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getIp() {
		return Config.ip;
	}

	public static int getPort() {
		return Config.port;
	}

	public static String getMd5Code() {
		return Config.md5Code;
	}
}