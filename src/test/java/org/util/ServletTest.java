package org.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.rest.Pathurl;

/**
 * 
 * @author cike
 *
 */
public class ServletTest {

	// @Test
	public void testRoute()
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object rs = new Pathurl().invoke("/clientid/user");
		System.out.println(rs);
	}

	@Test
	public void testClass() throws ClassNotFoundException {
		File file = new File("E:\\vagrant\\java\\org\\target\\.\\WEB-INF\\classes\\org\\rest\\Clientid.class");
		Pathurl.path(file.getAbsolutePath());
	}

}
