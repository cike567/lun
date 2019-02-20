package org.util;

import java.net.URI;

import org.auth.Rest;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class RestGrizzly {

	public static HttpServer starup(int port) {
		String uri = String.format("http://localhost:%s/", port);
		final ResourceConfig rc = new ResourceConfig();
		new Rest().getPackages().forEach(p -> {
			rc.packages(p);
		});
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), rc);
	}

}
