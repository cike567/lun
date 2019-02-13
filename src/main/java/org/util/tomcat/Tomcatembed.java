package org.util.tomcat;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.util.Embed;
import org.util.Jar;

/**
 * 
 * @author cike
 *
 */
public class Tomcatembed extends Embed {

	protected void webinf(String dir) throws IOException {
		String WEBAPP = "/src/main/webapp";
		Jar.cp(WEBAPP, dir);
	}

	protected Tomcatembed webapp(String path, String dir) throws Throwable {
		File webapp = new File(dir);
		StandardContext ctx = (StandardContext) tomcat.addWebapp(path, webapp.getAbsolutePath());
		ctx.setParentClassLoader(Tomcatembed.class.getClassLoader());
		File webInf = new File("classes");
		WebResourceRoot resources = new StandardRoot(ctx);
		WebResourceSet resourceSet = new DirResourceSet(resources, "/WEB-INF/classes", webInf.getAbsolutePath(), "/");
		resources.addPreResources(resourceSet);
		ctx.setResources(resources);
		return this;
	}

	protected void startup(int port) throws Throwable {
		// System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow",
		// "{}");

		tomcat.setPort(port);
		tomcat.getConnector();
		tomcat.start();
		tomcat.getServer().await();
	}

	public Tomcatembed() {
		super();
		tomcat = new Tomcat();
		// TODO
		// root = tomcat.addContext("/", new File(".").getAbsolutePath());
	}

	public static Tomcatembed embed() {
		return embed;
	}

	private static Tomcatembed embed = new Tomcatembed();

	private Tomcat tomcat;

	private Context root;

}
