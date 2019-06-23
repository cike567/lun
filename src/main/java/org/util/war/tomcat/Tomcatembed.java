package org.util.war.tomcat;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.util.Jar;
import org.util.war.AbstractEmbed;

/**
 * 
 * @author cike
 *
 */
public class Tomcatembed extends AbstractEmbed {

	@Override
	protected void webinf(String dir) throws IOException {
		Jar.cp(WEBAPP, dir);
		Jar.cp(CLASSES, dir + CLASSES);
	}

	@Override
	protected Tomcatembed webapp(String path, String dir) throws Throwable {
		File webapp = new File(dir);
		StandardContext ctx = (StandardContext) tomcat.addWebapp(path, webapp.getAbsolutePath());
		ctx.setParentClassLoader(Tomcatembed.class.getClassLoader());
		File webInf = new File(dir + CLASSES);
		WebResourceRoot resources = new StandardRoot(ctx);
		WebResourceSet resourceSet = new DirResourceSet(resources, CLASSES, webInf.getAbsolutePath(), "/");
		resources.addPreResources(resourceSet);
		ctx.setResources(resources);
		return this;
	}

	@Override
	protected Tomcatembed webapp(String war) throws Throwable {
		tomcat.setBaseDir(".");
		tomcat.getHost().setAppBase(".");
		tomcat.addWebapp("/", new File(war).getAbsolutePath());
		return this;
	}

	@Override
	protected void startup(int port) throws Throwable {
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
