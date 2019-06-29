package org.rest.rs;

import java.lang.annotation.Annotation;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

public class QueryParamrs extends Paramrs {

	public QueryParamrs(HttpServletRequest request) {// , Method method
		super(request);
	}

	@Override
	protected Enumeration<String> names(HttpServletRequest request) {
		return request.getParameterNames();
	}

	@Override
	protected void put(HttpServletRequest request, String name) {
		String p = request.getParameter(name);
		if (p != null) {
			headerMap.put(name, p);
		}
	}

	@Override
	protected Object param(Annotation a) {
		Object param = null;
		if (a instanceof QueryParam) {
			QueryParam p = (QueryParam) a;
			param = param(p.value());
		}
		return param;
	}

}
