package org.mvn;

import java.io.IOException;

import org.util.Jar;

/**
 * 
 * @author cike
 *
 */
public class App {
	/*
	 * -c 9222 -t 8080
	 */
	public static void main(String[] args) throws IOException {
		new App(args);
	}

	private App(String[] arg) throws IOException {
		new Jar();
	}

	// private Map<String, Object> args;

}
