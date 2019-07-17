package org.util;

import org.junit.Test;
import org.rili.Leave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class RiliTest {

	@Test
	public void date() {
		int year = 2019;
		System.out.println(new Leave(year));
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
