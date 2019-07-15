package org.db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws SQLException {
		String sql = Mapper.query(method);
		logger.debug("proxy:{},method:{},args:{},sql:{}", proxy, method.getName(), Arrays.toString(args), sql);
		Object rs = invoke(sql, args);
		logger.debug("invoke:{}", rs);
		if (rs != null) {
			rs = resultSet(rs, method.getReturnType());
		}
		return rs;
	}

	/**
	 * jdbc默认druid
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	private Object invoke(String sql, Object[] args) throws SQLException {
		Object rs = null;
		boolean query = sql.toLowerCase().startsWith("select");
		if (query) {
			rs = DruidMapper.query(sql, args);
		} else {
			DruidMapper.execute(sql, args);
		}
		return rs;
	}

	public Object resultSet(Object obj, Class returnType) {
		Object rs = null;
		if (returnType.isAssignableFrom(List.class)) {
			rs = obj;
		} else {
			List<Map<String, Object>> list = (List<Map<String, Object>>) obj;
			if (list.size() == 1) {
				Map<String, Object> map = list.get(0);
				if (returnType.isPrimitive() || returnType == String.class) {
					rs = map.values().toArray()[0];
				} else {
					rs = map;
				}
			}
		}
		return rs;
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
