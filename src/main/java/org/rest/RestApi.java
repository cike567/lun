package org.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;

import org.rest.rs.Pathrs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://localhost:9000/application.json
 * 
 * @author cike
 *
 */
@ApplicationPath("/")
@Path("/")
public class RestApi extends Application {

	public RestApi() {
	}

	public RestApi(Method method) {
		Path path = method.getAnnotation(Path.class);
		if (path != null) {
			this.path = path.value();
			this.method = GET.class.getSimpleName();
			if (method.getAnnotation(POST.class) != null) {
				this.method = POST.class.getSimpleName();
			}
			try {
				params(method);
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	@Path("application.json")
	public String api() {
		List<RestApi> apis = new ArrayList<RestApi>();
		Map<String, Method> paths = Pathrs.paths();
		for (Entry<String, Method> entry : paths.entrySet()) {
			if ("/application.json".equals(entry.getKey())) {
				continue;
			}
			apis.add(new RestApi(entry.getValue()));
		}
		logger.info("paths:{}-{}", paths, apis);
		return apis.toString();
	}

	private void params(Method method) throws ClassNotFoundException {
		List<String> rs = new ArrayList<String>();
		Type[] types = method.getGenericParameterTypes();
		Annotation[][] pas = method.getParameterAnnotations();
		for (int i = 0; i < types.length; i++) {
			String value = "";
			if (pas[i].length > 0) {
				if (filterClass.contains(pas[i][0].annotationType())) {
					continue;
				} else {
					value = value(pas[i][0]);
				}
			} else {
				continue;
			}
			if (filterString.contains(types[i].getTypeName())) {
				continue;
			}
			String name = Class.forName(types[i].getTypeName()).getSimpleName();
			rs.add(String.format("%s %s", name, value));
		}
		params = rs.toString().replaceAll("\\[|\\]", "");
	}

	private String value(Annotation a) {
		String value = "";
		if (a instanceof PathParam) {
			value = ((PathParam) a).value();
		} else if (a instanceof QueryParam) {
			value = ((QueryParam) a).value();
		}
		return value;
	}

	@Override
	public String toString() {
		return String.format("%s %s (%s)", method, path, params);
	}

	List<Class> filterClass = Arrays
			.asList(new Class[] { HeaderParam.class, CookieParam.class, HttpServletRequest.class });

	private List<String> filterString = Arrays.asList(new String[] { HttpServletRequest.class.getName(), "byte[]" });

	private String path;
	private String method;
	private String params;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
