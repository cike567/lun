package org.util.tomcat;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * 
 * @author cike
 *
 */
public class Tomcatembed {

	public Tomcatembed addServlet(Object servlet, String path) {
		String name = servlet.getClass().getSimpleName();
		System.out.println("" + name);
		tomcat.addServlet(root, name, (javax.servlet.Servlet) servlet);
		root.addServletMappingDecoded(String.format("/%s/*", path), name);
		return this;
	}

	public Tomcatembed addServlet(Object servlet) {
		String path = servlet.getClass().getSimpleName().replace("Servlet", "").toLowerCase();
		return addServlet(servlet, path);
	}

	public Tomcatembed webapp(String path, String dir) {
		tomcat.setBaseDir(dir);
		tomcat.getHost().setAppBase(dir);
		tomcat.getHost().setDeployOnStartup(true);
		tomcat.getHost().setAutoDeploy(true);
		tomcat.enableNaming();
		tomcat.addWebapp(path, new File(dir).getAbsolutePath());
		tomcat.addWebapp(STATIC.toLowerCase(), new File(dir + STATIC).getAbsolutePath());
		return this;
	}

	public void startup(int port, String path, String dir) throws LifecycleException {
		tomcat.addWebapp(path, dir);
		startup(port);
	}

	public void startup(int port) throws LifecycleException {
		// System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow",
		// "{}");
		tomcat.setPort(port);
		tomcat.getConnector();
		tomcat.start();
		tomcat.getServer().await();
	}

	public Tomcatembed() {
		tomcat = new Tomcat();
		root = tomcat.addContext("/", new File(".").getAbsolutePath());
	}

	public static Tomcatembed tomcat() throws IOException, InterruptedException {
		return embed;
	}

	private static Tomcatembed embed = new Tomcatembed();

	private Tomcat tomcat;

	private Context root;

	private String STATIC = "/static";
}
