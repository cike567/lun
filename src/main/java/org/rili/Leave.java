package org.rili;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.App;

import lombok.Data;

/**
 * 
 * @author cike
 *
 */
@Data
public class Leave {

	public Leave(String name, String holiday) {
		this(name, holiday, "");
	}

	public Leave(String name, String holiday, String weekday) {
		this.name = name;
		this.weekdays = Arrays.asList(weekday.split(","));
		this.holidays = Arrays.asList(holiday.split(","));
	}

	public Leave(int year) {
		name = String.valueOf(year);
		weekdays = new ArrayList<String>();
		holidays = new ArrayList<String>();
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
		String csv = App.get(RILI, RILI);
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

	/**
	 * 
	 */
	private static List<Leave> Y2019 = new ArrayList<Leave>() {
		{
			add(new Leave("元旦", "2018-12-30,2018-12-31,2019-01-01", "2018-12-29"));
			add(new Leave("春节", "2019-02-04,2019-02-05,019-02-06,2019-02-07,2019-02-08,2019-02-09,2019-02-10",
					"2019-02-02,2019-02-03"));
			add(new Leave("清明节", "2019-04-05"));
			add(new Leave("劳动节", "2019-05-01,2019-05-02,2019-05-03,2019-05-04", "2019-04-28,2019-05-05"));
			add(new Leave("中秋节", "2019-09-13"));
			add(new Leave("国庆节", "2019-10-01,2019-10-02,2019-10-03,2019-10-04,2019-10-05,2019-10-06,2019-10-07",
					"2019-09-29,2019-10-12"));
		}
	};

	@Override
	public String toString() {
		return String.format("%s:%s-%s", name, holidays, weekdays);
	}

	private final String RILI = "rili";
	private final String CSV = "%s.csv";

	private String name;
	private List<String> weekdays;
	private List<String> holidays;

}
