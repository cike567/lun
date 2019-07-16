package org.rest.rs;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public abstract class Paramrs {

	protected Paramrs() {
	}

	public Paramrs(HttpServletRequest request) {// , Method method
		params(request);
		logger.info("param:{}", paramMap);
	}

	/**
	 * 
	 * @param request
	 */
	protected abstract void params(HttpServletRequest request);

	/**
	 * 
	 * @param pas
	 * @return
	 */
	protected abstract Object param(Annotation pas);

	public Object param(String key) {
		Object param = null;
		if (paramMap.containsKey(key)) {
			param = paramMap.get(key);
		}
		return param;
	}

	public Map<String, Object> toMap() {
		return paramMap;
	}

	protected Map<String, Object> paramMap = new HashMap<String, Object>();
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
