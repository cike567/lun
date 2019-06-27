package org.rest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

public class Pathurl {

	public Pathurl() {
		URL url = this.getClass().getClassLoader().getResource("/org.rest".replaceAll("\\.", "/"));
		File dir = new File(url.getFile());
		System.out.println(dir);
		path(dir);
		System.out.println("class:" + classList);
		// Clientid clientid = new Clientid();
		classList.forEach((o) -> {
			try {
				path(o.newInstance());
			} catch (InstantiationException e) {
				System.out.println(e);
			} catch (IllegalAccessException e) {
				System.out.println(e);
			}
		});

	}

	public Object invoke(String path, Object... args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("path:" + path);
		Method method = methodMap.get(path);
		// TODO
		if (method == null) {
			return null;
		}
		System.out.println("method:" + method.getName() + "," + method.getDeclaringClass());
		return method.invoke(classeMap.get(path(method.getDeclaringClass(), method)), args);
	}

	private void path(File dir) {
		// URL url=this.getClass().getClassLoader().getResource(root);
		// File dir=new File(url.getFile());
		for (File file : dir.listFiles()) {
			System.out.println(file.getAbsolutePath());
			if (file.isDirectory()) {
				path(file);
			} else if (file.getName().endsWith(CLASS)) {
				Class clazz = path(file.getAbsolutePath());
				if (clazz != null) {
					classList.add(clazz);
				}
			}
		}
	}

	private void path(Object obj) {
		Class classes = obj.getClass();
		String p1 = path(classes);
		if (p1.equals("")) {
			return;
		}
		Method[] mds = classes.getMethods();
		for (Method method : mds) {
			String p2 = path(method);
			if ("".equals(p2)) {
				continue;
			}
			String mp = p1 + p2;
			String cp = path(classes, method);
			methodMap.put(mp, method);
			classeMap.put(cp, obj);
		}
	}

	private String path(Class classes, Method method) {
		return String.format("%s.%s", classes.getName(), method.getName());
	}

	private String path(Method method) {
		String path = "";
		if (method.isAnnotationPresent(Path.class)) {
			path = method.getAnnotation(Path.class).value();
		}
		return path;
	}

	private String path(Class classes) {
		Path path = (Path) classes.getAnnotation(Path.class);
		return path.value();
	}

	public static Class path(String className) {
		// String className = file.getAbsolutePath();
		int i = className.indexOf(CLASSES);
		if (i > 0) {
			className = className.substring(i + CLASSES.length() + 1, className.length() - CLASS.length())
					.replace(System.getProperty("file.separator"), ".");
		}
		Class clazz = null;
		try {
			clazz = Class.forName(className);
			if (!clazz.isAnnotationPresent(Path.class)) {
				clazz = null;
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return clazz;

	}

	private final static String CLASS = ".class";
	private final static String CLASSES = "classes";

	private Map<String, Method> methodMap = new HashMap<String, Method>();
	private Map<String, Object> classeMap = new HashMap<String, Object>();
	private List<Class> classList = new ArrayList<Class>();
}
