package org.util.war;

import java.io.File;
import java.io.IOException;

import org.Jar;
import org.War;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

/**
 * 
 * @author cike
 *
 */
public class Tomcatembed extends War {

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
		tomcat.setPort(port);
		tomcat.getConnector();
		tomcat.start();
		tomcat.getServer().await();
	}

	public Tomcatembed() {
		tomcat = new Tomcat();
	}

	public static Tomcatembed embed() {
		return embed;
	}

	private static Tomcatembed embed = new Tomcatembed();

	private Tomcat tomcat;

}
