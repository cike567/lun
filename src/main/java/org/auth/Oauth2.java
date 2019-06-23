package org.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.util.CacheList;
import org.util.URLMap;
import org.util.ws.Jaxrs;

/**
 * 
 * @author cike
 *
 */
//@WebServlet(urlPatterns = { "/oauth2/token" })
@Path("/oauth2.0")
public class Oauth2 {

	// @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@GET
	@Path("/token")
	public String token(@QueryParam(CLIENT_ID) String id, @QueryParam(CLIENT_SECRET) String secret) {
		// TODO
		return authorize(id).get(ACCESS_TOKEN).toString();
	}

	/**
	 * http://localhost:8080/oauth2.0/authorize?redirect_uri=http://xh.5156edu.com/pinyi.html
	 * 
	 * @param uri
	 * @return
	 */
	@GET
	@Path("/authorize")
	public Response authorize(@QueryParam(REDIRECT_URI) String uri, @QueryParam(CLIENT_ID) String appid) {
		String p = uri.indexOf("?") > 0 ? "&" : "?";
		// TODO
		String url = uri + p + URLMap.toQuery(authorize(appid));
		return Jaxrs.redirect(url);
	}

	private Map<String, Object> authorize(String appid) {
		// TODO
		int expire = new Random().nextInt(60);
		String token = UUID.randomUUID().toString();
		CacheList.put(token, expire);
		Map<String, Object> auth = new HashMap<String, Object>(5) {
			{
				put(ACCESS_TOKEN, token);
				put(EXPIRES_IN, expire);
			}
		};
		return auth;
	}

	// private CacheMap map = new CacheMap();

	public final static String ACCESS_TOKEN = "access_token";
	private final String EXPIRES_IN = "expires_in";
	private final String REDIRECT_URI = "redirect_uri";
	private final String CLIENT_ID = "client_id";
	private final String CLIENT_SECRET = "client_secret";

}
