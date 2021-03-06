package org;

import org.util.war.Tomcatembed;

/**
 * 
 * @author cike
 *
 */

public class App {
	/**
	 * java -jar a.jar 9000 b.war
	 */
	public static void main(String[] args) throws Throwable {
		Jar.classpath();

		new App(args);
	}

	private App(String[] arg) throws Throwable {
		War embed = Tomcatembed.embed();
		if (arg.length > 0) {
			port = Integer.parseInt(arg[0]);
		}
		if (arg.length == MAX) {
			String war = arg[1];
			embed.startup(war, port);
		} else {
			embed.startup("/", ".", port);
		}
	}

	private int port = 9000;

	private final int MAX = 2;

}
