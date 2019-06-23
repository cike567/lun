package org.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * http://localhost:8080/application.wadl
 * 
 * @author cike
 *
 */
@ApplicationPath("/")
public class RestApp extends Application {

	public Set<String> getPackages() {
		Set<String> sets = new HashSet<String>();
		getClasses().forEach(c -> {
			sets.add(c.getPackage().getName());
		});
		System.out.println("Application:" + sets);
		return sets;
	}

}
