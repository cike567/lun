package org.rest;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.rest.rs.Pathrs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://localhost:8080/application.wadl
 * 
 * @author cike
 *
 */
@ApplicationPath("/")
@Path("/")
public class RestApi extends Application {

	public RestApi() {
	}

	private RestApi(Entry<String, Method> entry) {
		this.path = entry.getKey();
		method(entry.getValue());
	}

	@Path("application.json")
	public String api() {
		List<RestApi> apis = new ArrayList<RestApi>();
		Map<String, Method> paths = Pathrs.paths();
		for (Entry<String, Method> entry : paths.entrySet()) {
			String path = entry.getKey();
			if ("/application.json".equals(entry.getKey())) {
				continue;
			}
			apis.add(new RestApi(entry));
		}
		logger.info("paths:{}-{}", paths, apis);
		return apis.toString();
	}

	private void method(Method method) {
		this.method = GET.class.getSimpleName();
		if (method.getAnnotation(POST.class) != null) {
			this.method = POST.class.getSimpleName();
		}
		Type[] types = method.getGenericParameterTypes();
		params = Arrays.toString(types);
	}

	@Override
	public String toString() {
		return String.format("%s %s (%s)", method, path, params);
	}

	private String path;
	private String method;
	private String params;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
