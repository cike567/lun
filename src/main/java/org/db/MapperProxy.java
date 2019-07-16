package org.db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 
 * @author cike
 *
 */
public class MapperProxy {

	public static <T> T getMapper(Class<T> classes) {
		return getMapper(classes, handler);
	}

	public static <T> T getMapper(Class<T> classes, InvocationHandler handle) {
		// MapperProxy.handler = handler;
		return (T) Proxy.newProxyInstance(classes.getClassLoader(), new Class[] { classes }, handle);
	}

	public static InvocationHandler handler = new MapperHandler() {
		{
			Mapper.sql();
			DruidMapper.connect();
		}
	};

}
