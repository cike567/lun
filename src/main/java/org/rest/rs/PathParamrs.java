package org.rest.rs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class PathParamrs {

	public PathParamrs(String url, Method method) {
		this.url = url;
		path(method);
		param();
	}

	public PathParamrs(String url, String path) {
		this.url = url;
		this.path = path;
		param();
	}

	private void path(Method method) {
		if (method.isAnnotationPresent(Path.class)) {
			Path p = method.getDeclaringClass().getAnnotation(Path.class);
			String cp = p.value();
			String mp = method.getAnnotation(Path.class).value();
			path = cp + mp;
		}
	}

	public Object param(Annotation[] pas) {
		Object param = null;
		for (Annotation a : pas) {
			if (a instanceof PathParam) {
				PathParam p = (PathParam) a;
				param = paramMap.get(p.value());
			}
		}
		return param;
	}

	private void param() {
		List<String> rs = new ArrayList();
		Matcher m = regex.matcher(path);
		while (m.find()) {
			String v = m.group();
			rs.add(v.substring(1, v.length() - 1));
		}
		if (rs.size() == 0) {
			return;
		}
		String[] ps = regex.split(path);
		String args = url.substring(ps[0].length());
		for (int i = 1; i <= ps.length; i++) {
			int j = -1;
			if (ps.length > i) {
				j = args.indexOf(ps[i]);
			}
			String v = args;
			if (j > -1) {
				v = args.substring(0, j);
				args = args.substring(j + 1);
			}
			paramMap.put(rs.get(i - 1), v);
		}
	}

	public Map<String, Object> toMap() {
		return paramMap;
	}

	private String path;
	private String url;
	private Map<String, Object> paramMap = new HashMap<String, Object>();
	private Pattern regex = Pattern.compile("\\{[\\w]*\\}");
}
