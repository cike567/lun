package org.util;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;
import org.rest.Clientid;
import org.rest.rs.Methodrs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class RSTest extends HttpServletTest {

	@Test
	public void testMethod() {
		Method[] methods = Clientid.class.getMethods();
		for (Method method : methods) {
			if (method.getName().equals("post")) {// post
				Object[] args = new Methodrs().params(request, method);
				logger.info("params={}", Arrays.toString(args));
			}
		}
		System.out.println(System.nanoTime());
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
