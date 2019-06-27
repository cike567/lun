package org.rest;

import java.io.InputStream;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author cike
 *
 */
@Path("/clientid")
public class Clientid {

	@GET
	@Path("/user")
	public String user() {// @PathParam("user") String user
		return "user";
	}

	@POST
	@Path("/body")
	public String body(InputStream input) {
		Scanner scanner = new Scanner(input, "UTF-8");
		return scanner.useDelimiter("\\A").next();

	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public String post(String body) {
		return body;
	}

}
