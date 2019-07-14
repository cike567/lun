package org.db;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

public class DruidMapper {

	static {
		try {
			load(new File("src/main/resources/db/jdbc.properties"));
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}

	public static void execute(String sql, Object... parameters) throws SQLException {
		JdbcUtils.execute(dataSource, sql, parameters);
	}

	public static List<Map<String, Object>> query(String sql, Object... parameters) throws SQLException {
		return JdbcUtils.executeQuery(dataSource, sql, parameters);
	}

	private static void load(File file) throws Throwable {
		System.out.println("jdbc:" + file.getAbsolutePath());
		Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream(file)) {
			properties.load(input);
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		}
	}

	private static DataSource dataSource;
}
