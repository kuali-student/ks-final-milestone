/*
 * 
 */
package org.kuali.student.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides a simple mechanism for tracking user activity on the system
 * 
 * @author Jeff Caddel
 */
public class ClickTrailFilter implements Filter {
	Runtime runtime = Runtime.getRuntime();

	/**
	 * Servlet container is starting up
	 */
	public void init(FilterConfig config) throws ServletException {
		log.info("System Startup Initiated: " + System.currentTimeMillis() + " Available Processors: " + runtime.availableProcessors() + " Maximum Memory: " + runtime.maxMemory());
	}

	/**
	 * Execute the filter logic
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// First point of known contact
		long firstByteDetected = System.currentTimeMillis();

		// Always default to UTF-8
		request.setCharacterEncoding("UTF-8");

		// Always a ServletWebContext for us
		ServletWebContext context = new ServletWebContext(((HttpServletRequest) (request)).getSession(true).getServletContext(), (HttpServletRequest) request, (HttpServletResponse) response);

		// The id of this HttpSession
		String id = context.getRequest().getSession().getId();

		RecordedRequest rr = logic.recordRequest(context);
		rr.setBefore(new Date(firstByteDetected));

		// Log all requests (even those that are not recorded)
		StringBuffer sb = new StringBuffer();
		sb.append("before: guid:" + guid + "; ");
		sb.append("sessionId:" + id + "; ");
		sb.append("before:" + firstByteDetected + "; ");
		log.info(sb.toString());

		chain.doFilter(request, response);

		rr.setFinishTime(new Date());

	}

	/**
	 * Servlet container is shutting down
	 */
	public void destroy() {
		log.info("System Shutdown Initiated: " + System.currentTimeMillis() + " Available Processors: " + runtime.availableProcessors() + " Maximum Memory: " + runtime.maxMemory());
	}
}
