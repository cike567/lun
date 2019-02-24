package org.util;

import java.util.Map;
import java.util.Map.Entry;

public class URLMap {

	public static String toQuery(Map<String, Object> query) {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, Object> entry : query.entrySet()) {
			sb.append(String.format("&%s=%s", entry.getKey(), entry.getValue()));
		}
		return sb.toString().substring(1);
	}

}
