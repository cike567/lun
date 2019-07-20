package org.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class NianTest {

	@Test
	public void date() {
		int year = 2019;
		// System.out.println(new Leave(year));
		System.out.println(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.THURSDAY)));
		System.out.println(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));

	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
