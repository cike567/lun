package org.util;

import java.io.File;

import org.AnnotationLoader;
import org.junit.Test;

/**
 * 
 * @author cike
 *
 */
public class ServletTest {

	@Test
	public void testPath() throws Throwable {
		String body = "jaxrs";
		Object rs = null;// new Pathrs().invoke("/clientid/post", body.getBytes());
		System.out.println(rs);
	}

	// @Test
	public void testClass() throws ClassNotFoundException {
		File file = new File("E:\\vagrant\\java\\org\\target\\.\\WEB-INF\\classes\\org\\rest\\Clientid.class");
		AnnotationLoader.classes(file.getAbsolutePath());
	}

}
