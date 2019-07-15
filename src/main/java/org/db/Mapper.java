package org.db;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.AnnotationLoader;

public class Mapper {

	public static void sql() {
		List<Class> classes = AnnotationLoader.classes(NamedQueries.class);
		classes.forEach((c) -> {
			query(c);
		});
		// query(OauthMapper.class);
	}

	public static void query(Class classes) {
		Method[] methods = classes.getMethods();
		Map<String, Method> methodMap = new HashMap(methods.length);
		for (Method method : methods) {
			methodMap.put(method.getName(), method);
		}
		NamedQuery[] queries = ((NamedQueries) classes.getAnnotation(NamedQueries.class)).value();
		for (NamedQuery query : queries) {
			String name = query.name();
			if (!mapperMap.containsKey(name)) {
				Map method = new HashMap(1);
				method.put(methodMap.get(name), query.query());
				mapperMap.put(name, method);
			} else {
				System.out.println("重名" + name);
			}
		}
	}

	public static String query(Method method) {
		String query = "";
		String name = method.getName();
		if (mapperMap.containsKey(name)) {
			query = mapperMap.get(name).get(method);
		}
		return query;
	}

	public static Map<String, Map<Method, String>> mapperMap = new HashMap();

}
