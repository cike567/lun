package org.rili;

import java.time.LocalDate;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
@Path("/holiday")
public class Holiday {

	@Path("/year/{year}")
	public String year(@PathParam("year") Integer year) {
		logger.debug("year:{}", year);
		return Leave.on(year).getHolidays().toString();
	}

	@Path("/month/{year}/{month}")
	public String month(@PathParam("year") Integer year, @PathParam("month") Integer month) {
		logger.debug("month:{}-{}", year, month);
		Leave leave = Leave.on(year);
		return leave.month(leave.getHolidays(), month).toString();
	}

	@Path("/week/{week}")
	public String week(@PathParam("week") Integer week) {
		Leave leave = Leave.on(LocalDate.now().getYear());
		return leave.week(leave.getHolidays(), week).toString();
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
