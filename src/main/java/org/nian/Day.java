package org.nian;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author cike
 *
 */
public class Day {

	public Day(String day) {
		if (day == null) {
			day = LocalDate.now().toString();
		}
		String[] days = day.split(",");
		for (String d : days) {
			if (d.indexOf(":") > 0) {
				String[] date = d.split(":");
				if (date.length != 2) {
					continue;
				}
				LocalDate b = LocalDate.parse(date[0]);
				LocalDate e = LocalDate.parse(date[1]);
				while (b.isBefore(e)) {
					put(b.toString());
					b = b.plusDays(1);
				}
				put(date[1]);
			} else {
				put(d);
			}
		}
	}

	private void put(String d) {
		String[] date = d.split("-|/");
		if (date.length == 3) {
			Integer year = Integer.parseInt(date[0]);
			List<String> dateList = new ArrayList<String>(9);
			if (dayMap.containsKey(year)) {
				dateList = dayMap.get(year);
			} else {
				dateList = new ArrayList<String>();
				dayMap.put(year, dateList);
			}
			dateList.add(d.replace("/", "-"));
		}
	}

	public Map<Integer, List<String>> toMap() {
		return dayMap;
	}

	private Map<Integer, List<String>> dayMap = new HashMap<Integer, List<String>>();

}
