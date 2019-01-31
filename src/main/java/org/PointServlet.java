package org;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.util.Stream;

@WebServlet(name = "PointServlet", urlPatterns = { "/point" })
public class PointServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String body = Stream.read(request.getInputStream());
		response.getWriter().write(body);
	}

}
