package org.rest.rs;

import java.lang.annotation.Annotation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.CookieParam;

public class CookieParamrs extends Paramrs {

	public CookieParamrs(HttpServletRequest request) {
		super(request);
	}

	@Override
	protected void params(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				paramMap.put(cookie.getName(), cookie.getValue());
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

}
