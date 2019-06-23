package org.util.ws;

import java.net.URI;

import javax.ws.rs.core.Response;

/**
 * 
 * @author cike
 *
 */
public class Jaxrs {

	public static Response redirect(String url) {
		return Response.seeOther(URI.create(url)).build();
	}

}
