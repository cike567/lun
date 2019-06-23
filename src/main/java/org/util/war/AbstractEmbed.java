package org.util.war;

import java.io.IOException;

/**
 * 
 * @author cike
 *
 */
public abstract class AbstractEmbed {

	/**
	 * 
	 * @param dir
	 * @throws IOException
	 */
	protected abstract void webinf(String dir) throws IOException;

	/**
	 * 
	 * @param path
	 * @param dir
	 * @return
	 * @throws Throwable
	 */
	protected abstract AbstractEmbed webapp(String path, String dir) throws Throwable;

	/**
	 * 
	 * @param port
	 * @throws Throwable
	 */
	protected abstract void startup(int port) throws Throwable;

	public void startup(String path, String dir, int port) throws Throwable {
		webinf(dir);
		webapp(path, dir).startup(port);
	}

	/**
	 * 
	 * @param war
	 * @return
	 * @throws Throwable
	 */
	protected abstract AbstractEmbed webapp(String war) throws Throwable;

	public void startup(String war, int port) throws Throwable {
		webapp(war).startup(port);
	}

	protected final String WEBAPP = "/src/main/webapp";
	protected final String CLASSES = "/WEB-INF/classes";

}
