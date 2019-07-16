package org.db;

import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * 
 * @author cike
 *
 */
@WebServlet(urlPatterns = { "/druid/*" })
public class DruidServlet extends StatViewServlet {

}
