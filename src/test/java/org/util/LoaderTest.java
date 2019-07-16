package org.util;

import java.io.File;
import java.util.Map;

import org.AnnotationLoader;
import org.junit.Test;
import org.rest.rs.PathParamrs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class LoaderTest {

	@Test
	public void testClass() throws ClassNotFoundException {
		File file = new File("E:\\vagrant\\java\\org\\target\\.\\WEB-INF\\classes\\org\\rest\\Clientid.class");
		AnnotationLoader.classes(file.getAbsolutePath());
	}

	@Test
	public void testRegex() {
		String path = "/cliendid/{name}/{id}";
		String url = "/cliendid/cike/1";
		Map map = new PathParamrs(url, path).toMap();
		System.out.println(map);
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
