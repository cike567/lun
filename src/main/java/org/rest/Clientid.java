package org.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.db.MapperProxy;
import org.oauth2.OauthMapper;
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
	@Path("/user/{user}")
	public String user(@PathParam("user") String user) {//
		String rs = "";
		logger.debug("user:{}", user);
		OauthMapper mapper = MapperProxy.getMapper(OauthMapper.class);
		logger.debug("mapper:{}", mapper);
		Map map = mapper.selectByClientId(user);
		if (map != null) {
			rs = map.toString();
		}
		return rs;
	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public String post(byte[] b, String body, @QueryParam("name") String name,
			@HeaderParam("content-length") Integer length, @CookieParam("sid") String sid, HttpServletRequest request) {
		return body + name + length;
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
