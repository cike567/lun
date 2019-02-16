package org;

import java.io.IOException;
import java.util.Map;

import org.util.Jar;

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
		Jar.cp(WEBAPP, root);
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
