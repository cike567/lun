package org;

import java.io.InputStream;
import java.util.Properties;

import org.util.war.Tomcatembed;

/**
 * 
 * @author cike
 *
 */

public class App {
	/**
	 * java -jar nian.jar 9000
	 */
	public static void main(String[] args) throws Throwable {
		Jar.classpath();
		conf();
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

	public static Object get(String key) {
		return properties.get(key);
	}

	public static String get(String key, String value) {
		if (properties.containsKey(key)) {
			value = properties.get(key).toString();
		}
		return value;
	}

	public static void conf() {
		try (InputStream input = App.class.getClassLoader().getResourceAsStream(CONF)) {
			properties.load(input);
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}

	private static Properties properties = new Properties();

	private final static String CONF = "application.properties";
	public final static String JDBC = "jdbc";
	public final static String PROJ = "project";

	private int port = 9000;

	private final int MAX = 2;

}
