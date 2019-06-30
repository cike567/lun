package org.rest.rs;

import java.lang.annotation.Annotation;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HeaderParam;

public class HeaderParamrs extends Paramrs {

	public HeaderParamrs(HttpServletRequest request) {
		super(request);
	}

	@Override
	protected Enumeration<String> names(HttpServletRequest request) {
		return request.getHeaderNames();
	}

	@Override
	protected void put(HttpServletRequest request, String name) {
		paramMap.put(name, request.getHeader(name));
	}

	@Override
	protected Object param(Annotation a) {
		Object param = null;
		if (a instanceof HeaderParam) {
			HeaderParam p = (HeaderParam) a;
			param = param(p.value());
		}
		return param;
	}

}
