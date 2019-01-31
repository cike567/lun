package org.util.tomcat;

import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.util.Embed;

/**
 * 
 * @author cike
 *
 */
public class Tomcatembed extends Embed {
	/*
	 * public Tomcatembed addServlet(Class servlet, String path) throws Throwable {
	 * String name = servlet.getSimpleName(); System.out.println("" + name);
	 * tomcat.addServlet(root, name, (javax.servlet.Servlet) servlet.newInstance());
	 * root.addServletMappingDecoded(path, name);// String.format("/%s/*", path)
	 * return this; }
	 */
	public Tomcatembed webapp(String path, String dir) throws Throwable {
		File webapp = new File(dir);
		// root = tomcat.addWebapp(path, webapp.getAbsolutePath());
		StandardContext ctx = (StandardContext) tomcat.addWebapp(path, webapp.getAbsolutePath());
		ctx.setParentClassLoader(this.getClass().getClassLoader());
		File webInf = new File("classes");
		WebResourceRoot resources = new StandardRoot(ctx);
		WebResourceSet resourceSet = new DirResourceSet(resources, "/WEB-INF/classes", webInf.getAbsolutePath(), "/");
		resources.addPreResources(resourceSet);
		ctx.setResources(resources);
		return this;
	}

	public void startup(int port, String path, String dir) throws Throwable {
		webapp(path, dir).startup(port);
	}

	public void startup(int port) throws Throwable {
		// System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow",
		// "{}");

		tomcat.setPort(port);
		tomcat.getConnector();
		tomcat.start();
		tomcat.getServer().await();
	}

	public Tomcatembed() {
		tomcat = new Tomcat();
		// TODO
		// root = tomcat.addContext("/", new File(".").getAbsolutePath());
	}

	public static Tomcatembed embed() {
		return embed;
	}

	private static Tomcatembed embed = new Tomcatembed();

	private Tomcat tomcat;

	// private Context root;

}
