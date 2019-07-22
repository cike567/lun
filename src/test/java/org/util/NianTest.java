package org.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.junit.Test;
import org.nian.Day;
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
		logger.info("{}", LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.THURSDAY)));
		logger.info("{}", LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));

		logger.info("{}", new Day("2019-07-11,2019-07-12:2019-09-13,2018/11/01").toMap().toString());
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
