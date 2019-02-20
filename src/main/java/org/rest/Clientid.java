package org.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/clientid")
public class Clientid {

	@GET
	@Path("/user")
	public String user() {
		return "user";
	}

}
