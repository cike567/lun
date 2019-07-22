package org.nian;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
@Path("/weekday")
public class Weekday {

	@Path("/year/{year}")
	public String year(@PathParam("year") Integer year) {
		logger.debug("year:{}", year);
		return Leave.on(year).getWeekdays().toString();
	}

	@Path("/month/{year}/{month}")
	public String month(@PathParam("year") Integer year, @PathParam("month") Integer month) {
		logger.debug("month:{}-{}", year, month);
		Leave leave = Leave.on(year);
		return leave.month(leave.getWeekdays(), month).toString();
	}

	@Path("/week/{week}")
	public String week(@PathParam("week") Integer week) {
		Leave leave = Leave.on(LocalDate.now().getYear());
		return leave.week(leave.getWeekdays(), week).toString();
	}

	@Path("/day/{day}")
	public String day(@PathParam("day") String day) {
		Map<Integer, List<String>> dayMap = new Day(day).toMap();
		Set<String> set = new TreeSet<String>();
		for (Entry<Integer, List<String>> entry : dayMap.entrySet()) {
			Leave leave = Leave.on(entry.getKey());
			set.addAll(leave.day(leave.getWeekdays(), entry.getValue()));
		}
		return set.toString();
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
