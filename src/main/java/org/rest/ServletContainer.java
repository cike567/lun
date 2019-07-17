package org.rest;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.AnnotationLoader;
import org.db.DruidMapper;
import org.db.Mapper;
import org.rest.rs.Pathrs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
@WebServlet(urlPatterns = { "/*" })
public class ServletContainer extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding(Charset);
		// super.service(req, resp);
		try {
			Object rs = path.invoke(request);
			if (rs != null) {
				logger.info("return :{}", rs);
				response.getOutputStream().write(rs.toString().getBytes(Charset));
			} else if (!welcome(request, response)) {
				e404(response);
			}
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("config" + config);
		AnnotationLoader.load();
		Mapper.sql();
		DruidMapper.connect();
		path = new Pathrs();
	}

	private void e404(HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	private boolean welcome(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		boolean flag = false;
		if (ROOT.equals(request.getRequestURI())) {
			request.getRequestDispatcher("/application.json").forward(request, response);
			flag = true;
		}
		return flag;
	}

	private final String ROOT = "/";
	private final String Charset = "UTF-8";

	private Pathrs path;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
