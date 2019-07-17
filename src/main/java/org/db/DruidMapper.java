package org.db;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.App;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

/**
 * 
 * @author cike
 *
 */
public class DruidMapper {

	public static void connect() {
		String jdbc = App.get("jdbc", JDBC);
		Properties properties = new Properties();
		try (InputStream input = DruidMapper.class.getClassLoader().getResourceAsStream(jdbc)) {
			properties.load(input);
			dataSource = DruidDataSourceFactory.createDataSource(properties);
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

	private static final String JDBC = "db/jdbc.properties";

	private static DataSource dataSource;
}
