package org.util;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.AnnotationLoader;
import org.db.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.oauth2.OauthMapper;
import org.rest.Clientid;
import org.rest.rs.Methodrs;
import org.rest.rs.Pathrs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class RsTest extends HttpServletTest {

	@Before
	public void mapper() {
		AnnotationLoader.put(Clientid.class);
		Mapper.query(OauthMapper.class);
	}

	@Test
	public void testMethod() {
		Method[] methods = Clientid.class.getMethods();
		for (Method method : methods) {
			if ("user".equals(method.getName())) {
				Object[] args = new Methodrs().params(request, method);
				logger.info("params={}", Arrays.toString(args));
			}
		}
		System.out.println(System.nanoTime());
	}

	@Test
	public void testPath() throws Throwable {
		Object rs = new Pathrs().invoke(request);
		System.out.println(rs);
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
