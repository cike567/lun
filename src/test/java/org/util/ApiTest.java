package org.util;

import java.lang.reflect.Method;

import org.junit.Test;
import org.rest.Clientid;
import org.rest.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class ApiTest {

	@Test
	public void testMethod() {
		Method[] methods = Clientid.class.getMethods();
		for (Method method : methods) {
			logger.info("method:{}", method);
			logger.info("api:{}", new RestApi(method));
		}
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
