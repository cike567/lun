package org.rest.rs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Methodrs {

	public Object[] params(HttpServletRequest request, Method method) {
		Type[] types = method.getGenericParameterTypes();
		Object[] args = new Object[types.length];

		Paramrs header = new HeaderParamrs(request);
		// TODO body顺序
		Paramrs query = new QueryParamrs(request);
		Paramrs cookie = new CookieParamrs(request);
		Bodyrs body = new Bodyrs(request);
		// TODO
		Map<String, Object> paramMap = new HashMap<String, Object>() {
			{
				put("byte[]", body.toByte());
				put(String.class.getName(), body.toString());
				put(HttpServletRequest.class.getName(), request);
			}
		};
		Annotation[][] pas = method.getParameterAnnotations();
		for (int i = 0; i < types.length; i++) {
			// header
			Object p = header.param(pas[i]);
			// param
			if (p == null) {
				p = query.param(pas[i]);
			}
			// cookie
			if (p == null) {
				p = cookie.param(pas[i]);
			}
			//
			if (p == null) {
				p = paramMap.get(types[i].getTypeName());
			}

			args[i] = p;
		}

		return args;
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
