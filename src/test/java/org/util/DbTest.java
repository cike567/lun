package org.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.App;
import org.db.DruidMapper;
import org.db.Mapper;
import org.db.MapperHandler;
import org.db.MapperProxy;
import org.junit.Before;
import org.junit.Test;
import org.oauth2.OauthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class DbTest {

	@Before
	public void mapper() {
		App.conf();
		Mapper.query(OauthMapper.class);
		DruidMapper.connect();
	}

	@Test
	public void testDruid() throws SQLException {
		String sql = "SELECT * FROM OAUTH_CLIENT_DETAILS";
		List list = DruidMapper.query(sql);
		logger.debug("{}", list);
	}

	@Test
	public void testMapper() throws SQLException {
		OauthMapper mapper = MapperProxy.getMapper(OauthMapper.class);
		List list = mapper.selectOauthClientDetails();
		logger.debug("List:{}", list);
		Map map = mapper.selectByClientId("test");
		logger.debug("Map:{}", map);
		String oauth = mapper.selectAuthorized("test", "123456");
		logger.debug("clumn:{}", oauth);
	}

	@Test
	public void testResultSet() {
		new MapperHandler().resultSet(new ArrayList(), Map.class);
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
