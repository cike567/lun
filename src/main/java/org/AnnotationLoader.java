package org;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationLoader {

	public static void load() {
		URL url = AnnotationLoader.class.getResource("");
		System.out.println("url:" + url.getFile() + url.getProtocol());
		// TODO
		// Linux
		if (url.toString().startsWith("jar:")) {
			try {
				Jar.classes(url).forEach((f -> {
					load(f);
				}));
				System.out.println("jar:" + classMap);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		} else {// Windows
			File dir = new File(url.getFile());
			path(dir);
		}

	}

	public static void load(File file) {
		if (file.getName().endsWith(CLASS)) {
			put(classes(file.getAbsolutePath()));
		}
	}

	public static void load(List<File> files) {
		System.out.println("class files:" + files);
		files.forEach((file) -> {
			if (file.getName().endsWith(CLASS)) {
				put(classes(file.getAbsolutePath()));
			}
		});
	}

	private static void path(File dir) {
		for (File file : dir.listFiles()) {
			System.out.println(file.getAbsolutePath());
			if (file.isDirectory()) {
				path(file);
			} else {
				load(file);
			}
		}
	}

	public static void put(Class clazz) {
		if (clazz != null) {
			for (Annotation a : clazz.getAnnotations()) {
				List<Class> classes;
				Class k = a.annotationType();
				if (classMap.containsKey(k)) {
					classes = classMap.get(k);
				} else {
					classes = new ArrayList<Class>();
					classMap.put(k, classes);
				}
				classes.add(clazz);
			}
		}
	}

	public static Class classes(String fileName) {
		Class clazz = null;
		int i = fileName.indexOf(CLASSES);
		if (i > 0) {
			String className = fileName.substring(i + CLASSES.length() + 1, fileName.length() - CLASS.length())
					.replace(System.getProperty("file.separator"), ".");
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		return clazz;

	}

	public static List<Class> classes(Class clazz) {
		if (classMap.size() == 0) {
			AnnotationLoader.load();
		}
		return classMap.containsKey(clazz) ? classMap.get(clazz) : new ArrayList<Class>();
	}

	private final static String CLASS = ".class";
	private final static String CLASSES = "classes";
	public static Map<Class, List<Class>> classMap = new HashMap<Class, List<Class>>();
}
