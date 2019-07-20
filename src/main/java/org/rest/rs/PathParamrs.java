package org.rest.rs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * 
 * @author cike
 *
 */
public class PathParamrs extends Paramrs {

	public PathParamrs(HttpServletRequest request, Method method) {
		super(request);
		param(method);
		logger.info("param:{}", paramMap);
	}

	public PathParamrs(String url, String path) {
		this.url = url;
		this.path = path;
		param();
	}

	@Override
	protected void params(HttpServletRequest request) {
		this.url = request.getRequestURI();
	}

	@Override
	protected Object param(Annotation a) {
		Object param = null;
		if (a instanceof PathParam) {
			PathParam p = (PathParam) a;
			param = paramMap.get(p.value());
		}
		return param;
	}

	private void param(Method method) {
		if (method.isAnnotationPresent(Path.class)) {
			Path p = method.getDeclaringClass().getAnnotation(Path.class);
			String cp = p.value();
			String mp = method.getAnnotation(Path.class).value();
			path = cp + mp;
		}
		param();
	}

	private void param() {
		List<String> rs = new ArrayList<String>();
		Matcher m = regex.matcher(path);
		while (m.find()) {
			String v = m.group();
			rs.add(v.substring(1, v.length() - 1));
		}
		if (rs.size() == 0) {
			return;
		}
		String[] ps = match(path);
		if (url.length() > ps[0].length()) {
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
	}

	public static String[] match(String path) {
		return regex.split(path);
	}

	public static String path(String path) {
		String[] ps = match(path);
		if (!path.equals(ps[0]) || ps.length > 1) {
			path = String.join("", ps).replaceAll("/+", "/");
		}
		return path;
	}

	public static String path(Method method) {
		String path = null;
		if (method.isAnnotationPresent(Path.class)) {
			String p = method.getAnnotation(Path.class).value();
			path = path(p);
		}
		return path;
	}

	public static String path(Class classes) {
		Path path = (Path) classes.getAnnotation(Path.class);
		return path.value();
	}

	private String path;
	private String url;

	private static Pattern regex = Pattern.compile("\\{[\\w]*\\}");

}
