package org.util;

import java.io.IOException;

/**
 * 
 * @author cike
 *
 */
public abstract class Embed {

	protected abstract void webinf(String dir) throws IOException;

	protected abstract Embed webapp(String path, String dir) throws Throwable;

	protected abstract void startup(int port) throws Throwable;

	public void startup(int port, String path, String dir) throws Throwable {
		webinf(dir);
		webapp(path, dir).startup(port);
	}

}
