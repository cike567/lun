package org.db;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

/**
 * 
 * @author cike
 *
 */
public class DruidMapper {

	public static void connect() {
		File file = new File("WEB-INF/classes/db" + jdbc);
		if (!file.exists()) {
			file = new File("src/main/resources/db" + jdbc);
		}
		try {

			load(file);
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}

	public static void execute(String sql, Object... parameters) throws SQLException {
		if (parameters == null) {
			JdbcUtils.execute(dataSource, sql);
		} else {
			JdbcUtils.execute(dataSource, sql, parameters);
		}
	}

	public static List<Map<String, Object>> query(String sql, Object... parameters) throws SQLException {
		List<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
		if (parameters == null || parameters.length == 0) {
			rs = JdbcUtils.executeQuery(dataSource, sql);
		} else {
			rs = JdbcUtils.executeQuery(dataSource, sql, parameters);
		}
		return rs;
	}

	private static void load(File file) throws Throwable {
		System.out.println("jdbc:" + file.getAbsolutePath());
		Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream(file)) {
			properties.load(input);
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		}
	}

	private static String jdbc = "/jdbc.properties";

	private static DataSource dataSource;
}
