package org.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Jar {

	public Jar() throws IOException {
		this(Jar.class.getResource(MF));
	}

	public Jar(URL url) throws IOException {
		URL[] urls = classpath(url);
		log = LoggerFactory.getLogger(this.getClass());
		log.info("jar:{}", Arrays.toString(urls));
	}

	private URL[] classpath(URL url) throws IOException {
		// URL url = Jar.class.getResource(MF);
		String root = url.getFile().replace(MF, "");
		System.out.println(root);
		List<String> lines = Stream.readLine(url.openStream());
		boolean FLAG = false;
		StringBuffer sb = new StringBuffer();
		for (String line : lines) {
			if (line.startsWith(CLASS_PATH)) {
				FLAG = true;
				sb.append(line.substring(CLASS_PATH.length()).trim());
				continue;
			}
			if (FLAG) {
				if (line.indexOf(":") == -1) {
					sb.append(line.substring(1));
				} else {
					FLAG = false;
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
			url = new URL(String.format("jar:%s/%s", root, names[i]));// "jar:" +
			File file = new File(names[i]);
			Stream.write(file, url.openStream());
			urls[i] = file.toURL();
		}
		return urls;
	}

	public void cp(String path, String dir) throws IOException {
		URL url = Jar.class.getResource(path);
		if (url != null) {
			cp(url, new File(dir));
		}
	}

	public void cp(URL url, File dir) throws IOException {
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		JarFile jarFile = jarURLConnection.getJarFile();
		String path = new Strings(url.getPath()).sub("jar!/").toString();
		System.out.println(path);
		for (Enumeration<JarEntry> enumeration = jarFile.entries(); enumeration.hasMoreElements();) {
			JarEntry jarEntry = enumeration.nextElement();
			String name = jarEntry.getName();
			if (name.contains(path)) {
				File file = new File(dir, name.replace(path, ""));
				Stream.write(file, jarFile.getInputStream(jarEntry));
			}
		}
	}

	private final static String MF = "/META-INF/MANIFEST.MF";

	private final String CLASS_PATH = "Class-Path:";

	private Logger log;

}
