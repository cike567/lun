package org.util;

/**
 * 
 * @author cike
 *
 */
public abstract class Embed {

	public Embed addServlet(Object servlet) {
		String path = servlet.getClass().getSimpleName().replace("Servlet", "").toLowerCase();
		return addServlet(servlet, path);
	}

	public abstract Embed addServlet(Object servlet, String path);

	public abstract Embed webapp(String path, String dir);

	public abstract void startup(int port, String path, String dir);

	public abstract void startup(int port);

}
