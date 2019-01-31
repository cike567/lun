package org.util;

/**
 * 
 * @author cike
 *
 */
public abstract class Embed {
	/*
	 * public Embed addServlet(Class servlet) throws Throwable { String path =
	 * servlet.getSimpleName().replace("Servlet", "").toLowerCase(); return
	 * addServlet(servlet, String.format("/%s", path)); }
	 * 
	 * public abstract Embed addServlet(Class servlet, String path) throws
	 * Throwable;
	 */
	public abstract Embed webapp(String path, String dir) throws Throwable;

	public abstract void startup(int port, String path, String dir) throws Throwable;

	public abstract void startup(int port) throws Throwable;

	// protected String STATIC = "/static";

}
