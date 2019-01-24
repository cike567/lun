package org.util;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

/**
 * 
 * @author cike
 *
 */
public class JarTest {

	@Test
	public void testRun() throws IOException {
		String name = "jar:file:/E:/vagrant/java/org/target/mvn-1.0-SNAPSHOT-bin.jar!/META-INF/MANIFEST.MF";
		URL url = new URL(name);
		new Jar(url);
	}

}
