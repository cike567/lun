package org.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

public class HttpBasicAuth {

	HttpBasicAuth(ServletRequest request, ServletResponse response) {
		this.request = (HttpServletRequest) request;
		this.response = (HttpServletResponse) response;
		addAuth("user", UUID.randomUUID().toString());
	}

	public void doFilter(FilterChain chain) throws IOException, ServletException {
		boolean flag = auth(request);
		if (flag) {
			chain.doFilter(request, response);
		} else {
			e401(response);
		}
	}

	public void addAuth(String user, String password) {
		basic.add(auth(user, password));
	}

	private String auth(String user, String password) {
		System.out.println(password);
		return "Basic " + new BASE64Encoder().encodeBuffer(String.format("%s:%s", user, password).getBytes()).trim();
	}

	private boolean auth(HttpServletRequest request) throws IOException {
		String auth = request.getHeader("Authorization");
		return basic.contains(auth);
	}

	private void e401(HttpServletResponse response) throws IOException {
		response.setHeader("WWW-Authenticate", "Basic realm=\"HTTP Basic Auth\"");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

	private HttpServletRequest request;

	private HttpServletResponse response;

	private static List<String> basic = new ArrayList<String>();

}
