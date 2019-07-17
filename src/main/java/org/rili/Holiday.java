package org.rili;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/holiday/v1")
public class Holiday {

	@Path("/year/{year}")
	public String year(@PathParam("year") Integer year) {
		return leave(year).toString();
	}

	private Leave leave(int year) {
		Leave leave = null;
		if (leaveMap.containsKey(year)) {
			leave = leaveMap.get(year);
		} else {
			leave = new Leave(year);
			leaveMap.put(year, leave);
		}
		return leave;
	}

	private Map<Integer, Leave> leaveMap = new HashMap<Integer, Leave>(9);

}
