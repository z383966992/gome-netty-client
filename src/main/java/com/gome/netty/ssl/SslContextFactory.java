package com.gome.netty.ssl;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class SslContextFactory {
	private static final String PROTOCOL = "TLS"; // TODO: which protocols will
													// be adopted?
	private static final SSLContext SERVER_CONTEXT;
	private static final SSLContext CLIENT_CONTEXT;

	static {

		SSLContext serverContext = null;
		SSLContext clientContext = null;

		// get keystore and trustore locations and passwords
		String keyStorePassword = "!@#gomeqwZXcloud#$%";
		

		try {
			// truststore
			KeyStore ts = KeyStore.getInstance("jceks");
			ts.load(SslContextFactory.class.getClassLoader()
					.getResourceAsStream("cert\\cgomecloud.keystore"),
					keyStorePassword.toCharArray());

			// set up trust manager factory to use our trust store
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ts);
			clientContext = SSLContext.getInstance(PROTOCOL);
			clientContext.init(null, tmf.getTrustManagers(),
					null);
		} catch (Exception e) {
			throw new Error("Failed to initialize the client-side SSLContext",
					e);
		}

		SERVER_CONTEXT = serverContext;
		CLIENT_CONTEXT = clientContext;
	}

	public static SSLContext getServerContext() {
		return SERVER_CONTEXT;
	}

	public static SSLContext getClientContext() {
		return CLIENT_CONTEXT;
	}

	private SslContextFactory() {
		// Unused
	}
}