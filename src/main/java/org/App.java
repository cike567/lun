package org;

import java.util.Map;

import org.util.Jar;
import org.util.Shell;
import org.util.war.AbstractEmbed;
import org.util.war.tomcat.Tomcatembed;

/**
 * 
 * @author cike
 *
 */
public class App {
	/**
	 * -w a.war -p 9000
	 */
	public static void main(String[] args) throws Throwable {
		Jar.classpath();
		new App(args);
	}

	private App(String[] arg) throws Throwable {

		AbstractEmbed embed = Tomcatembed.embed();
		Map<String, Object> args = Shell.args(arg);

		if (args.containsKey(P)) {
			port = (Integer) args.get(P);
		}

		if (args.containsKey(W)) {
			String war = args.get(W).toString();
			embed.startup(war, port);
		} else {
			// .addServlet(EndServlet.class).addServlet(PointServlet.class,"/json/ws")
			embed.startup("/", ".", port);
		}
	}

	private final String W = "w";

	private final String P = "p";

	private int port = 9000;

}
