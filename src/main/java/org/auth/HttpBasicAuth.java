package org.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.util.CacheList;

import sun.misc.BASE64Encoder;

/**
 * 
 * @author cike
 *
 */
public class HttpBasicAuth {

	HttpBasicAuth(ServletRequest request, ServletResponse response) {
		this.request = (HttpServletRequest) request;
		this.response = (HttpServletResponse) response;
		System.out.println(basic);
	}

	public void doFilter(FilterChain chain) throws IOException, ServletException {
		boolean flag = auth(request);
		if (flag) {
			chain.doFilter(request, response);
		} else {
			e401(response);
		}
	}

	private boolean auth(HttpServletRequest request) throws IOException {
		System.out.println(request.getPathInfo());
		boolean flag = startsWithFilter(whiteList, request.getPathInfo());
		if (!flag) {
			String token = request.getParameter(Oauth2.ACCESS_TOKEN);
			flag = CacheList.get(token) != null;
		}
		if (!flag) {
			String auth = request.getHeader("Authorization");
			flag = basic.contains(auth);
		}
		return flag;
	}

	boolean startsWithFilter(List<String> list, String path) {
		if (path == null) {
			return true;
		}
		System.out.println("path:" + path);
		return list.stream().filter(t -> path.startsWith(t)).collect(Collectors.toList()).size() > 0;
	}

	private void e401(HttpServletResponse response) throws IOException {
		response.setHeader("WWW-Authenticate", "Basic realm=\"HTTP Basic Auth\"");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

	private static String auth(String user, String password) {
		System.out.println(password);
		return "Basic " + new BASE64Encoder().encodeBuffer(String.format("%s:%s", user, password).getBytes()).trim();
	}

	private static List<String> basic = new ArrayList<String>() {
		{
			add(auth("root", "cike"));
			add(auth("user", UUID.randomUUID().toString()));
		}
	};

	private List<String> whiteList = new ArrayList<String>() {
		{
			add("/application.wadl");
			add("/favicon.ico");
			add("/oauth2.0");
		}
	};

	// TODO
	private List<String> blackList = new ArrayList<String>() {
		{
			add("/oauth2/authorize");
		}
	};

	private HttpServletRequest request;

	private HttpServletResponse response;

}
