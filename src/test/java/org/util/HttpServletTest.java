package org.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class HttpServletTest {
	@Mock
	protected HttpServletRequest request;

	@Mock
	protected HttpServletResponse response;

	@Before
	public void mock() throws IOException {
		MockitoAnnotations.initMocks(this);
		String body = "{\"cike\":\"刺客\"}";
		int length = body.getBytes().length;
		// header
		Enumeration<String> headNames = Collections.enumeration(Arrays.asList(new String[] { "content-length" }));
		Mockito.when(request.getHeaderNames()).thenReturn(headNames);
		Mockito.when(request.getHeader("content-length")).thenReturn(String.valueOf(length));
		// param
		Enumeration<String> paramNames = Collections.enumeration(Arrays.asList(new String[] { "name" }));
		Mockito.when(request.getParameterNames()).thenReturn(paramNames);
		Mockito.when(request.getParameter("name")).thenReturn("cike");

		// inputStream
		Mockito.when(request.getContentLength()).thenReturn(length);
		Mockito.when(request.getInputStream()).thenReturn(getInputStream(body.getBytes()));

		// cookie
		Cookie cookie = new Cookie("sid", "abc");
		// getCookies
		Mockito.when(request.getCookies()).thenReturn(new Cookie[] { cookie });

		// path
		Mockito.when(request.getRequestURI()).thenReturn("/holiday/month");
	}

	public ServletInputStream getInputStream(byte[] body) throws IOException {
		final ByteArrayInputStream input = new ByteArrayInputStream(body);
		ServletInputStream servletInputStream = new ServletInputStream() {
			// @Override
			public boolean isFinished() {
				return false;
			}

			// @Override
			public boolean isReady() {
				return false;
			}

			// @Override
			public void setReadListener(ReadListener readListener) {

			}

			@Override
			public int read() throws IOException {
				return input.read();
			}
		};
		return servletInputStream;
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
