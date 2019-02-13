package org.util;

import org.junit.Test;
import org.util.tomcat.Tomcatembed;

public class EmbedTest {

	int port = 8080;

	@Test
	public void testTomcat() throws Throwable {
		Embed embed = Tomcatembed.embed();
		embed.webapp("/embed", WEBAPP)// .addServlet(EndServlet.class).addServlet(PointServlet.class)
				.startup(port);

	}

	public final static String WEBAPP = "src/main/webapp";

}
