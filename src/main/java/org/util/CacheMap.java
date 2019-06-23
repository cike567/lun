package org.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author cike
 *
 */
public class CacheMap {
	private static Map<String, Object> valueMap = new ConcurrentHashMap<>();
	private static Map<String, Long> expireMap = new ConcurrentHashMap<>();

	public void put(String key, Object value, Long expire) {
		valueMap.put(key, value);
		expireMap.put(key, expire);
	}

	public void put(String key, String value, int expire) {
		Long time = now() + expire;
		put(key, value, time);
	}

	public static Object get(String key) {
		Object val = null;
		if (key != null && valueMap.containsKey(key)) {
			if (expireMap.get(key) > now()) {
				val = valueMap.get(key);
			} else {
				valueMap.remove(key);
				expireMap.remove(key);
			}
		}
		return val;
	}

	private static Long now() {
		return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
	}

}
