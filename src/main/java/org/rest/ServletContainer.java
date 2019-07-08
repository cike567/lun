package org.rest;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.AnnotationLoader;
import org.rest.rs.Pathrs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = { "/*" })
public class ServletContainer extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		// super.service(req, resp);
		try {
			Object rs = path.invoke(request);
			logger.info("return :{}", rs);
			response.getWriter().print(rs);
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("config" + config);
		AnnotationLoader.load();
		path = new Pathrs();
	}

	private Pathrs path;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
