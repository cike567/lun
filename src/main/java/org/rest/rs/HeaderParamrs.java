package org.rest.rs;

import java.lang.annotation.Annotation;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HeaderParam;

/**
 * 
 * @author cike
 *
 */
public class HeaderParamrs extends Paramrs {

	public HeaderParamrs(HttpServletRequest request) {
		super(request);
	}

	@Override
	protected void params(HttpServletRequest request) {
		Enumeration<String> names = request.getHeaderNames();
		while (names != null && names.hasMoreElements()) {
			String name = names.nextElement();
			// headerMap.put(name, request.getHeader(name));
			paramMap.put(name, request.getHeader(name));
		}
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
