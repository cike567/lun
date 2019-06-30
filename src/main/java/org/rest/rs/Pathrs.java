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

	public Object invoke(String path, HttpServletRequest request) throws Throwable {
		Object rs = null;
		if (methodMap.containsKey(path)) {
			Method method = methodMap.get(path);
			Object[] args = new Methodrs().params(request, method);
			rs = method.invoke(classMap.get(className(method)), args);
		}
		return rs;
	}

	private void path(Object obj) {
		Class classes = obj.getClass();
		String p1 = path(classes);
		if (p1 == null) {
			return;
		}
		Method[] mds = classes.getMethods();
		for (Method method : mds) {
			String p2 = path(method);
			if (p2 == null) {
				continue;
			}
			String mp = p1 + p2;
			String cp = className(method);
			methodMap.put(mp, method);
			classMap.put(cp, obj);
		}
	}

	private String path(Method method) {
		String path = null;
		if (method.isAnnotationPresent(Path.class)) {
			path = method.getAnnotation(Path.class).value();
		}
		return path;
	}

	private String path(Class classes) {
		Path path = (Path) classes.getAnnotation(Path.class);
		return path.value();
	}

	private String className(Method method) {
		Class classes = method.getDeclaringClass();
		String className = String.format("%s.%s", classes.getName(), method.getName());
		logger.info("className:{}", className);
		return className;
	}

	private Map<String, Method> methodMap = new HashMap<String, Method>();
	private Map<String, Object> classMap = new HashMap<String, Object>();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
