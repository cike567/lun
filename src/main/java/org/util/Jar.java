package org.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Jar {

	public Jar() throws IOException {
		URL[] urls = classpath();
		log = LoggerFactory.getLogger(this.getClass());
		log.info("jar:{}", Arrays.toString(urls));
	}

	private URL[] classpath() throws IOException {
		URL url = Jar.class.getResource(MF);
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
					sb.append(line.trim());
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
			File file = write(url.openStream(), names[i]);
			urls[i] = file.toURL();
		}

		return urls;
	}

	private File write(InputStream input, String fileName) throws IOException {
		// file
		File file = new File(fileName);
		if (!file.exists()) {
			if (file.getParentFile() != null && !file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (file.isFile()) {
				file.createNewFile();
			}
		}
		Stream.write(input, file);
		input.close();
		return file;
	}

	private final String MF = "/META-INF/MANIFEST.MF";

	private final String CLASS_PATH = "Class-Path:";

	private Logger log;

}
