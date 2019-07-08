package org.rest.rs;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;

import org.AnnotationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pathrs {

	public Pathrs() {
		List<Class> classes = AnnotationLoader.classes(Path.class);
		// classes.add(Clientid.class);
		logger.info("classes:{}", classes.toString());
		classes.forEach((o) -> {
			try {
				path(o.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				logger.error(e.getMessage());
			}
		});
		logger.info("path_method:{}", methodMap.toString());
		logger.info("path_class:{}", classMap.toString());
	}

	public Object invoke(HttpServletRequest request) throws Throwable {
		Object rs = null;
		String path = path(request);
		logger.info("path:{}", path);
		if (methodMap.containsKey(path)) {
			Method method = methodMap.get(path);
			Object[] args = new Methodrs().params(request, method);
			rs = method.invoke(classMap.get(className(method)), args);
		}
		return rs;
	}

	private String path(HttpServletRequest request) {
		String path = request.getRequestURI();
		if (methodMap.containsKey(path)) {
			return path;
		}
		String[] ps = path.split("/");
		StringBuffer sb = new StringBuffer();
		for (String p : ps) {
			sb.append(p).append("/");
			if (methodMap.containsKey(sb.toString())) {
				path = sb.toString();
				break;
			}
		}
		return path;
	}

	private void path(Object obj) {
		Class classes = obj.getClass();
		String p1 = PathParamrs.path(classes);
		if (p1 == null) {
			return;
		}
		Method[] mds = classes.getMethods();
		for (Method method : mds) {
			String p2 = PathParamrs.path(method);
			if (p2 == null) {
				continue;
			}
			String mp = p1 + p2;
			String cp = className(method);
			methodMap.put(mp, method);
			classMap.put(cp, obj);
		}
	}

	private String className(Method method) {
		Class classes = method.getDeclaringClass();
		String className = String.format("%s.%s", classes.getName(), method.getName());
		logger.info("className:{}", className);
		return className;
	}

	public static Map paths() {
		return methodMap;
	}

	private static Map<String, Method> methodMap = new HashMap<String, Method>();
	private static Map<String, Object> classMap = new HashMap<String, Object>();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
