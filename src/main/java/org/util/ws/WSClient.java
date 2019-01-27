package org.util.ws;

import java.io.IOException;
import java.net.URI;

public abstract class WSClient {

	protected WSClient() {
	}

	public WSClient(String uri) throws Throwable {
		this(uri, new LogMessage());
	}

	public WSClient(String uri, Message message) throws Throwable {
		this.message = message;
		this.uri = new URI(uri);
		connect();
	}

	public abstract void connect() throws Throwable;

	public abstract String send(String text) throws IOException, InterruptedException;

	protected URI uri;

	protected Message message;

}