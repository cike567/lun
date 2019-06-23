package org.util.ws;

import java.io.IOException;
import java.net.URI;

/**
 * 
 * @author cike
 *
 */
public abstract class AbstractClientws {

	protected AbstractClientws() {
	}

	public AbstractClientws(String uri) throws Throwable {
		this(uri, new LogMessage());
	}

	public AbstractClientws(String uri, AbstractMessage message) throws Throwable {
		this.message = message;
		this.uri = new URI(uri);
		connect();
	}

	/**
	 * 
	 * @throws Throwable
	 */
	public abstract void connect() throws Throwable;

	/**
	 * 
	 * @param text
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public abstract String send(String text) throws IOException, InterruptedException;

	protected URI uri;

	protected AbstractMessage message;

}