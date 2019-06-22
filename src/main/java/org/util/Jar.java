package org.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 
 * @author cike
 *
 */
public class Jar {

	public static URL[] classpath() {
		URL[] urls = null;
		try {
			urls = classpath(Jar.class.getResource(MF));
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
		return urls;
	}

	public static URL[] classpath(URL url) throws IOException, Throwable {
		// URL url = Jar.class.getResource(MF);
		String root = url.getFile().replace(MF, "");
		System.out.println(root);
		List<String> lines = Stream.readLine(url.openStream());
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		for (String line : lines) {
			if (line.startsWith(CLASS_PATH)) {
				flag = true;
				sb.append(line.substring(CLASS_PATH.length()).trim());
				continue;
			}
			if (flag) {
				if (line.indexOf(":") == -1) {
					sb.append(line.substring(1));
				} else {
					flag = false;
					break;
				}
			}
		}
		if ("".equals(sb.toString())) {
			return new URL[0];
		}

		String[] names = sb.toString().split(" ");
		URL[] urls = new URL[names.length];
		for (int i = 0; i < names.length; i++) {
			url = new URL(String.format("jar:%s/%s", root, names[i]));
			File file = new File(names[i]);
			System.out.println("jar:" + file.getAbsolutePath());
			Stream.write(file, url.openStream());
			urls[i] = file.toURL();
		}
		return urls;
	}

	public static void cp(String path, String dir) throws IOException {
		URL url = Jar.class.getResource(path);
		if (url != null) {
			cp(url, new File(dir));
		}
	}

	public static void cp(URL url, File dir) throws IOException {
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		JarFile jarFile = jarURLConnection.getJarFile();
		String path = new Strings(url.getPath()).sub("jar!/").toString();
		System.out.println(path);
		for (Enumeration<JarEntry> enumeration = jarFile.entries(); enumeration.hasMoreElements();) {
			JarEntry jarEntry = enumeration.nextElement();
			String name = jarEntry.getName();
			if (name.contains(path)) {
				File file = new File(dir, name.replace(path, ""));
				System.out.println("cp:" + file.getAbsolutePath());
				Stream.write(file, jarFile.getInputStream(jarEntry));
			}
		}
	}

	private final static String MF = "/META-INF/MANIFEST.MF";

	private final static String CLASS_PATH = "Class-Path:";

	// private Logger log;
}
