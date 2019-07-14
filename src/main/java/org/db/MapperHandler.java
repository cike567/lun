package org.db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String sql = Mapper.query(method);
		Object rs = null;
		if (args == null) {
			rs = DruidMapper.query(sql);
		} else {
			rs = DruidMapper.query(sql, args);
		}
		return rs;
	}

}
