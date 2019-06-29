package org.util;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.AnnotationLoader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.rest.Clientid;
import org.rest.rs.Methodrs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class LoaderTest {

	

	// @Test
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

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
