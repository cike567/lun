package org.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import sun.misc.BASE64Encoder;

/**
 * 
 * @author cike
 *
 */
public class AuthTest {

	@Test
	public void testRun() throws IOException {
		List<String> basic = new ArrayList<String>();
		String p = UUID.randomUUID().toString();
		System.out.println(p);
		String auth = String.format("Basic %s", new BASE64Encoder().encodeBuffer(("user:" + p).getBytes()));
		basic.add(UUID.randomUUID().toString());
		basic.add(auth);
		System.out.println(basic + auth.trim() + basic.contains(auth));
	}

}
