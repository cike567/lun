package org.rest;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
@Path("/clientid")
public class Clientid {

	@GET
	@Path("/{user}")
	public String user(@PathParam("user") String user) {// @PathParam("user") String user
		return user;
	}

	@POST
	@Path("/body")
	public String body(byte[] b, @HeaderParam("content-length") String body, HttpServletRequest request) {
		logger.info("byte:{}", Arrays.toString(b));
		logger.info("content-length:{}", request.getHeader("content-length"));
		return body;

	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public String post(String b, @QueryParam("name") String name, @HeaderParam("content-length") Integer length) {
		return b.length() + name + length;// ,@CookieParam("sid") String sid
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
