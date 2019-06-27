package org;

import java.net.URL;
import java.util.Arrays;

public class AppTest {

	// @Test
	public void jar() throws Throwable {
		URL url = new URL("jar:file:/E:/vagrant/java/org/target/mvn-1.0-SNAPSHOT-bin.jar!/META-INF/MANIFEST.MF");
		URL[] urls = Jar.classpath(url);
		System.out.println(Arrays.toString(urls));
	}

}
