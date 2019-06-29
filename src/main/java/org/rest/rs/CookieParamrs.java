package org.rest.rs;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.CookieParam;

public class CookieParamrs extends Paramrs {

	public CookieParamrs(HttpServletRequest request) {// , Method method
		super(request);
	}

	@Override
	protected Enumeration<String> names(HttpServletRequest request) {
		List<String> list = new ArrayList<String>();
		for (Cookie cookie : request.getCookies()) {
			list.add(cookie.getName());
		}
		return Collections.enumeration(list);
	}

	@Override
	protected void put(HttpServletRequest request, String name) {
		for (Cookie cookie : request.getCookies()) {
			if (name.equals(cookie.getName())) {
				headerMap.put(name, cookie.getValue());
			}
		}
	}

	@Override
	protected Object param(Annotation a) {
		Object param = null;
		if (a instanceof CookieParam) {
			CookieParam p = (CookieParam) a;
			param = param(p.value());
		}
		return param;
	}

	private Bodyrs body;

}
