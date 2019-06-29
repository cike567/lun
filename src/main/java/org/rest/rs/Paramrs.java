package org.rest.rs;

import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class Paramrs {

	public Paramrs(HttpServletRequest request) {// , Method method
		// this.request = request;
		// params(method);
		params(request);
	}

	protected abstract Enumeration<String> names(HttpServletRequest request);

	protected abstract void put(HttpServletRequest request, String name);

	protected abstract Object param(Annotation pas);

	private void params(HttpServletRequest request) {
		// request.getHeaderNames()
		Enumeration<String> names = names(request);
		while (names != null && names.hasMoreElements()) {
			String name = names.nextElement();
			// headerMap.put(name, request.getHeader(name));
			put(request, name);
		}
	}

	public Object param(Annotation[] pas) {
		Object param = null;
		for (Annotation a : pas) {
			param = param(a);
			if (param != null) {
				break;
			}
		}
		return param;
	}

	public String param(String key) {
		String param = null;
		if (headerMap.containsKey(key)) {
			param = headerMap.get(key);
		}
		return param;
	}

	public Map<String, String> toMap() {
		return headerMap;
	}

	protected Map<String, String> headerMap = new HashMap<String, String>();

	// private HttpServletRequest request;
}
