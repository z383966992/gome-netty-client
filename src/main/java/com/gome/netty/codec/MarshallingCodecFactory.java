package com.gome.netty.codec;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import java.io.IOException;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

public final class MarshallingCodecFactory {

	/**
	 * 创建Jboss Marshaller
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Marshaller buildMarshalling() throws IOException {
		final MarshallerFactory marshallerFactory = Marshalling
				.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		Marshaller marshaller = marshallerFactory
				.createMarshaller(configuration);
		return marshaller;
	}

	/**
	 * 创建Jboss Unmarshaller
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Unmarshaller buildUnMarshalling() throws IOException {
		final MarshallerFactory marshallerFactory = Marshalling
				.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		final Unmarshaller unmarshaller = marshallerFactory
				.createUnmarshaller(configuration);
		return unmarshaller;
	}
	
	public static MarshallingDecoder buildMarshallingDecoder() {
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		
		configuration.setVersion(5);
		
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
		
		MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
		
		return decoder;
		
	}
	
	public static MarshallingEncoder buildMarshallingEncoder() {
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		return encoder;
	}
}
