package org;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

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
		System.out.println("root:" + root);
		List<String> lines = readLine(url.openStream());
		System.out.println("mf:" + lines);
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
			write(file, url.openStream());
			urls[i] = file.toURL();
		}
		return urls;
	}

	public static List<File> classes(URL url) throws IOException {
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		JarFile jarFile = jarURLConnection.getJarFile();
		String path = sub(url.getPath(), "jar!/");
		System.out.println("jar!:" + path);

		for (Enumeration<JarEntry> enumeration = jarFile.entries(); enumeration.hasMoreElements();) {
			JarEntry jarEntry = enumeration.nextElement();
			String name = jarEntry.getName();
			System.out.println("jar name:" + name);
			if (name.contains(path) && name.endsWith(CLASS)) {
				File file = new File(name);
				System.out.println("class file:" + file.getAbsolutePath());
				classFile.add(file);
			}
		}
		return classFile;
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
		String path = sub(url.getPath(), "jar!/");
		System.out.println("jar!:" + path);

		for (Enumeration<JarEntry> enumeration = jarFile.entries(); enumeration.hasMoreElements();) {
			JarEntry jarEntry = enumeration.nextElement();
			String name = jarEntry.getName();
			System.out.println("jar name:" + name);
			if (name.contains(path)) {
				File file = new File(dir, name.replace(path, ""));
				System.out.println("cp:" + file.getAbsolutePath());
				write(file, jarFile.getInputStream(jarEntry));
				// load(file);
			}
		}
	}

	public static List<File> classes() {
		return classFile;
	}

	private static String sub(String temp, String fix) {
		int index = temp.indexOf(fix);
		if (index > -1) {
			temp = temp.substring(temp.indexOf(fix) + fix.length());
		}
		return temp;
	}

	private static List<String> readLine(InputStream input) throws Throwable {
		return new BufferedReader(new InputStreamReader(input)).lines().parallel().collect(Collectors.toList());
	}

	private static void write(File file, InputStream input) throws IOException {
		if (!file.exists()) {
			String name = file.getName();
			if (file.isFile() || name.contains(".")) {
				if (file.getParentFile() != null && !file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			} else if (file.isDirectory() || !name.contains(".")) {
				file.mkdirs();
			}
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[input.available()];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		byte[] bytes = output.toByteArray();
		output.close();
		if (bytes.length > 0) {
			Files.write(Paths.get(file.getAbsolutePath()), bytes);
		}
	}

	private static List<File> classFile = new ArrayList<File>();

	private final static String MF = "/META-INF/MANIFEST.MF";

	private final static String CLASS_PATH = "Class-Path:";

	private final static String CLASS = ".class";

}
