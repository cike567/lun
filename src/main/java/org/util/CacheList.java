package org.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//TODO namaspace
public class CacheList {

	private static List<String> valueList = Collections.synchronizedList(new ArrayList());
	private static Map<String, Long> expireMap = new ConcurrentHashMap<>();

	public static void put(String value, Long expire) {
		valueList.add(value);
		expireMap.put(value, expire);
	}

	public static void put(String value, int expire) {
		Long time = now() + expire;
		put(value, time);
	}

	public static String get(String key) {
		String val = null;
		if (key != null && valueList.contains(key)) {
			if (expireMap.get(key) > now()) {
				val = key;
			} else {
				valueList.remove(key);
				expireMap.remove(key);
			}
		}
		return val;
	}

	private static Long now() {
		return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
	}

}
