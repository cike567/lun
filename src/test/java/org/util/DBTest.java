package org.util;

import java.sql.SQLException;
import java.util.List;

import org.db.DruidMapper;
import org.db.Mapper;
import org.junit.Test;
import org.oauth2.OauthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBTest {

	// @Test
	public void testDruid() throws SQLException {
		String sql = "SELECT * FROM OAUTH_CLIENT_DETAILS";
		List list = DruidMapper.query(sql);
		logger.debug("{}", list);
	}

	@Test
	public void testMapper() throws SQLException {
		OauthMapper mapper = Mapper.getMapper(OauthMapper.class);
		List list = mapper.selectOauthClientDetails();
		logger.debug("{}", list);
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
