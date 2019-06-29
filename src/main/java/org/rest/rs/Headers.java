package org.rest.rs;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Headers {

	public Headers(HttpServletRequest request) {// , Method method
		// this.request = request;
		// params(method);
		params(request);
	}

	private void params(HttpServletRequest request) {
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			headerMap.put(name, request.getHeader(name));
		}
	}

	public Map<String, String> toMap() {
		return headerMap;
	}

	private Map<String, String> headerMap = new HashMap<String, String>();

	// private HttpServletRequest request;
}
