package org;

import java.util.Map;

import org.util.Embed;
import org.util.Jar;
import org.util.Shell;
import org.util.jetty.Jettyembed;
import org.util.tomcat.Tomcatembed;

/**
 * 
 * @author cike
 *
 */
public class App {
	/*
	 * -w tomcat/jetty -t 9000
	 */
	public static void main(String[] args) throws Throwable {
		new App(args);
	}

	private App(String[] arg) throws Throwable {
		String root = "webapp";
		String WEBAPP = "/src/main/webapp";
		new Jar().cp(WEBAPP, root);
		Embed embed = Jettyembed.embed();
		args = Shell.args(arg);
		if (args.containsKey(W)) {
			if ("tomcat".equals(args.get(W))) {
				embed = Tomcatembed.embed();
			}
		}

		if (args.containsKey(P)) {
			port = (Integer) args.get(P);
		}

		embed.webapp("/embed", root)// .addServlet(EndServlet.class).addServlet(PointServlet.class, "/json/ws")
				.startup(port);

	}

	private String W = "w";

	private String P = "p";

	private int port = 9000;

	private Map<String, Object> args;

}
