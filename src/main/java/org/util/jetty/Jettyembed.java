package org.util.jetty;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.util.Embed;

public class Jettyembed extends Embed {

	/*
	 * @Override public Embed addServlet(Class servlet, String path) { //
	 * webapp.getServletHandler() servletHandler.addServletWithMapping(servlet,
	 * path); return this; }
	 */

	@Override
	public Embed webapp(String path, String dir) {

		webapp = new WebAppContext();
		webapp.setContextPath(path);
		webapp.setResourceBase("src/main/webapp/WEB-INF");
		webapp.setConfigurations(new Configuration[] { new AnnotationConfiguration(), new WebXmlConfiguration(),
				new WebInfConfiguration(), new PlusConfiguration(), new MetaInfConfiguration(),
				new FragmentConfiguration(), new EnvConfiguration() });
		webapp.setParentLoaderPriority(true);
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { webapp, new DefaultHandler() });
		server.setHandler(handlers);
		return this;
	}

	@Override
	public void startup(int port, String path, String dir) throws Throwable {
		// TODO Auto-generated method stub
		webapp(path, dir).startup(port);
	}

	@Override
	public void startup(int port) throws Throwable {
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(port);
		server.addConnector(connector);
		server.start();
		server.join();
	}

	private Jettyembed() {
		server = new Server();
		// servletHandler = new ServletHandler();

	}

	public static Jettyembed embed() {
		return embed;
	}

	private static Jettyembed embed = new Jettyembed();

	private Server server;

	// private ServletHandler servletHandler;

	private WebAppContext webapp;

}
