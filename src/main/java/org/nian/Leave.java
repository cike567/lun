package org.nian;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

/**
 * 
 * @author cike
 *
 */
@Data
public class Leave {

	private Leave(int year) {
		this.year = year;
		weekdays = new TreeSet<String>();
		holidays = new TreeSet<String>();
		weekday(year);
		int min = 1;
		LocalDate date = LocalDate.ofYearDay(year, min);
		int max = date.lengthOfYear();
		while (min <= max) {
			if (holiday(date)) {
				holidays.add(date.toString());
			} else {
				weekdays.add(date.toString());
			}
			min++;
			date = date.plusDays(1);
		}
	}

	private boolean holiday(LocalDate date) {
		boolean holiday = true;
		String day = date.toString();
		if (holidays.contains(day)) {
			holiday = true;
		} else if (weekdays.contains(day)) {
			holiday = false;
		} else if (date.getDayOfWeek().getValue() > 5) {
			holiday = true;
		} else {
			holiday = false;
		}
		return holiday;
	}

	private void weekday(int year) {
		String csv = App.get(App.PROJ, NIAN);
		String name = String.format(csv + "/" + CSV, year);
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(name);
		if (input != null) {
			List<String> leave = new BufferedReader(new InputStreamReader(input)).lines().parallel()
					.collect(Collectors.toList());
			leave.forEach((l) -> {
				String[] row = l.split(",");
				holidays.addAll(Arrays.asList(row[1].split(" ")));
				if (row.length == 3) {
					weekdays.addAll(Arrays.asList(row[2].split(" ")));
				}
			});
		}
	}

	public List<String> month(Set<String> days, Integer month) {
		month = month(month);
		String min = String.format("%s-%02d-01", year, month);
		String max = String.format("%s-%02d-31", year, month);
		return between(days, min, max);
	}

	public List<String> week(Set<String> days, Integer week) {
		LocalDate now = LocalDate.now();
		if (week != null) {
			now = now.plusWeeks(week);
		}
		LocalDate min = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDate max = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		return between(days, min.toString(), max.toString());
	}

	private List<String> between(Set<String> days, String min, String max) {
		logger.debug("{}:{},{}", min, max, days);
		return days.stream().filter((d) -> {
			return d.compareTo(min) >= 0 && d.compareTo(max) <= 0;
		}).collect(Collectors.toList());
	}

	public List<String> day(Set<String> days, List<String> dayList) {
		return dayList.stream().filter(d -> days.contains(d)).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return String.format("%s:%s-%s", year, holidays, weekdays);
	}

	public static Integer year(Integer year) {
		return year == null || year.equals(0) ? year = LocalDate.now().getYear() : year;
	}

	public static Integer month(Integer month) {
		return month == null ? month = LocalDate.now().getMonthValue() : month;
	}

	public static Leave on(Integer year) {
		year = year(year);
		Leave leave = null;
		if (leaveMap.containsKey(year)) {
			leave = leaveMap.get(year);
		} else {
			leave = new Leave(year);
			leaveMap.put(year, leave);
		}
		return leave;
	}

	private static Map<Integer, Leave> leaveMap = new HashMap<Integer, Leave>(9);

	private final String NIAN = "nian";
	private final String CSV = "%s.csv";

	private int year;
	private Set<String> weekdays;
	private Set<String> holidays;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
