package org.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author cike
 *
 */
public class Stream {

	public static String read(InputStream inputStream) {
		return read(inputStream, "UTF-8");
	}

	public static String read(InputStream inputStream, String charset) {
		Scanner scanner = new Scanner(inputStream, charset);
		return scanner.useDelimiter("\\A").next();
	}

	public static List<String> readLine(InputStream inputStream) throws IOException {
		List<String> lines = new ArrayList<String>();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		return lines;
	}

	public static File write(byte[] b, File file) throws IOException {
		if (!file.exists()) {
			String name = file.getName();
			if (file.isFile() || name.contains(".")) {
				if (file.getParentFile() != null && !file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			} else if (file.isDirectory() || !name.contains(".")) {//
				file.mkdirs();
			}
		}
		if (b.length > 0) {
			RandomAccessFile rf = new RandomAccessFile(file, "rw");
			System.out.println(String.format("f=%s,new=%d,old=%d", file.getAbsolutePath(), b.length, rf.length()));
			if (b.length != rf.length()) {
				rf.getChannel().truncate(0);
				rf.write(b);
				rf.close();
			}
		}
		return file;
	}

	public static File write(File file, InputStream input, String charset) throws IOException {
		ByteArrayOutputStream output = toByteArrayOutputStream(input);
		byte[] buffer = output.toString(charset).getBytes();
		output.close();
		return write(buffer, file);
	}

	public static File write(File file, InputStream input) throws IOException {
		ByteArrayOutputStream output = toByteArrayOutputStream(input);
		byte[] buffer = output.toByteArray();
		output.close();
		return write(buffer, file);
	}

	private static ByteArrayOutputStream toByteArrayOutputStream(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUF];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output;
	}

	private static final int BUF = 1024 * 8;

}
