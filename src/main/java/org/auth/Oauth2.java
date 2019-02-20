package org.auth;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

//@WebServlet(urlPatterns = { "/oauth2/token" })
@Path("/oauth2")
public class Oauth2 {

	// @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@GET
	@Path("/token")
	public Response token() {
		return Response.status(200).entity(UUID.randomUUID().toString()).build();
	}

}
