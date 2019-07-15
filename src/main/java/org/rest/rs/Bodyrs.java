package org.rest.rs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bodyrs {

	public Bodyrs(HttpServletRequest request) {
		// this.request = request;
		try {
			body(request);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void body(HttpServletRequest request) throws IOException {
		int length = request.getContentLength();
		logger.info("ContentLength:{}", length);
		if (length > 0) {
			byt = new byte[length];
			request.getInputStream().read(byt);
			// read(request);
			logger.info("body:{}", Arrays.toString(byt));
		}

	}

	private void read(HttpServletRequest request) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		InputStream input = request.getInputStream();
		byte[] body = new byte[request.getContentLength()];
		int b = 0;
		while (-1 != (b = input.read(body))) {
			output.write(body, 0, b);
		}
		byt = output.toByteArray();
		output.close();
	}

	public byte[] toByte() {
		return byt;
	}

	public String toString() {
		if (byt == null || byt.length == 0) {
			return "";
		}
		return new String(byt, Charset.forName("UTF-8"));
	}

	//// TODO toMap
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap();
		String body = toString();
		return map;
	}

	//// TODO toClass
	public <T> T toClass(Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage());
		}
		Map<String, Object> body = toMap();
		return t;
	}

	private byte[] byt;

	// private HttpServletRequest request;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
