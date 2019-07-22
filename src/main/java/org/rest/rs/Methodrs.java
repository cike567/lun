package org.rest.rs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.CookieParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class Methodrs {

	public Object[] params(HttpServletRequest request, Method method) {
		Type[] types = method.getGenericParameterTypes();
		Object[] args = new Object[types.length];

		header = new HeaderParamrs(request);
		// TODO body顺序
		query = new QueryParamrs(request);
		cookie = new CookieParamrs(request);
		path = new PathParamrs(request, method);
		Bodyrs body = new Bodyrs(request);
		// TODO
		Map<String, Object> paramMap = new HashMap<String, Object>(3) {
			{
				put("byte[]", body.toByte());
				put(String.class.getName(), body.toString());
				put(HttpServletRequest.class.getName(), request);
			}
		};
		Annotation[][] pas = method.getParameterAnnotations();
		logger.info("type:{}", Arrays.toString(types));
		for (int i = 0; i < types.length; i++) {
			Object p = null;
			if (pas[i].length > 0) {
				p = param(pas[i][0]);
			} else {
				p = paramMap.get(types[i].getTypeName());
			}
			args[i] = type(p, types[i]);
		}
		logger.info("args:{}", Arrays.toString(args));
		return args;
	}

	private Object param(Annotation a) {
		Object param = null;
		if (a instanceof HeaderParam) {
			param = header.param(((HeaderParam) a).value());
		} else if (a instanceof QueryParam) {
			param = query.param(((QueryParam) a).value());
		} else if (a instanceof CookieParam) {
			param = query.param(((CookieParam) a).value());
		} else if (a instanceof PathParam) {
			param = path.param(((PathParam) a).value());
		}
		return param;
	}

	private Object type(Object p, Type type) {
		if (p != null && !p.getClass().equals(type)) {
			if (type.equals(Integer.class)) {
				try {
					p = Integer.parseInt(p.toString());
				} catch (NumberFormatException e) {
					p = 0;
				}
			}
		}
		return p;
	}

	Paramrs header;
	Paramrs cookie;
	Paramrs query;
	PathParamrs path;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
