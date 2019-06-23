package org.rest;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.util.Stream;

/**
 * 
 * @author cike
 *
 */
@Path("/clientid")
public class Clientid {

	@GET
	@Path("/{user}")
	public String get(@PathParam("user") String user) {
		return user;
	}

	@POST
	@Path("/body")
	public String body(InputStream input) {
		String body = Stream.read(input);
		return body.toString();
	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public String post(String body) {
		return body;
	}

}
