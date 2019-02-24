package org.util;

import java.net.URI;

import javax.ws.rs.core.Response;

public class Jaxrs {

	public static Response redirect(String url) {
		return Response.seeOther(URI.create(url)).build();
	}

}
