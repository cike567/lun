package org;

import java.io.IOException;
import java.util.Map;

import org.util.Embed;
import org.util.Jar;
import org.util.Shell;
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
		String root = ".";
		// String WEBAPP = "/src/main/webapp";
		// new Jar().cp(WEBAPP, root);
		Embed embed = Tomcatembed.embed();
		args = Shell.args(arg);

		if (args.containsKey(P)) {
			port = (Integer) args.get(P);
		}

		embed.startup(port, "/", root);// .addServlet(EndServlet.class).addServlet(PointServlet.class, "/json/ws")
	}

	static {
		try {
			new Jar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String W = "w";

	private String P = "p";

	private int port = 9000;

	private Map<String, Object> args;

}
