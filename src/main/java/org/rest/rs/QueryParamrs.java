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
	protected void params(HttpServletRequest request) {
		Enumeration<String> names = request.getParameterNames();
		while (names != null && names.hasMoreElements()) {
			String name = names.nextElement();
			paramMap.put(name, request.getParameter(name));
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
