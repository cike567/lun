package org.rest.rs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HeaderParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Methodrs {

	public Methodrs(Method method) {
		Type[] types = method.getGenericParameterTypes();
		for (int i = 0; i < types.length; i++) {
			logger.info("GenericParameterType,{}-{}", types[i].getTypeName(), types[i].getClass());
		}
		params(method);
	}

	public Object[] params(HttpServletRequest request) {
		Map<String, String> header = new Headers(request).toMap();
		Bodyrs body = new Bodyrs(request);
		Object[] args = null;
		return args;
	}

	private void params(Method method) {
		Annotation[][] ps = method.getParameterAnnotations();
		if (ps == null || ps.length == 0) {
			return;
		}
		for (Annotation[] p : ps) {
			for (Annotation a : p) {
				System.out.println(a);
				if (a instanceof HeaderParam) {
					String name = ((HeaderParam) a).value();
					System.out.println(name);
				}
			}
		}
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
