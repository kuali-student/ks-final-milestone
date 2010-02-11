/*
 * 
 */
package org.kuali.student.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides a simple mechanism for tracking user activity on the system
 * 
 * @author Jeff Caddel
 */
public class ClickTrailFilter implements Filter {

	private static final String SESSION_RECORDER_KEY = "recordedSession";

	private static final Log log = LogFactory.getLog(ClickTrailFilter.class);
	Runtime runtime = Runtime.getRuntime();
	long sequence = 0;

	/**
	 * Servlet container is starting up
	 */
	public void init(FilterConfig config) throws ServletException {
		log.info("System Startup Initiated: " + System.currentTimeMillis() + " Available Processors: " + runtime.availableProcessors() + " Maximum Memory: " + runtime.maxMemory());
	}

	/**
	 * Execute the filter logic
	 */
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("sequence=" + (sequence++));
		HttpServletRequest request = (HttpServletRequest) req;
		RecordedRequest rr = recordRequest(request);
		rr.setStartTime(new Date());
		chain.doFilter(request, response);
		rr.setFinishTime(new Date());
	}

	/**
	 * Servlet container is shutting down
	 */
	public void destroy() {
		log.info("System Shutdown Initiated: " + System.currentTimeMillis() + " Available Processors: " + runtime.availableProcessors() + " Maximum Memory: " + runtime.maxMemory());
	}

	/**
	 * Record this HttpRequest.<br>
	 */
	public RecordedRequest recordRequest(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		RecordedSession rs = (RecordedSession) session.getAttribute(SESSION_RECORDER_KEY);
		if (rs == null) {
			log.info("Recording a new HTTP session: " + session.getId());
			rs = new RecordedSession();
			session.setAttribute(SESSION_RECORDER_KEY, rs);
		}

		// Copy the parameter list
		List<NameValueBean> parameters = getParameters(request);

		// Get a recorded request object
		RecordedRequest rr = getRecordedRequest(request, parameters);

		// GWT spews asynchronous requests
		synchronized (rs) {
			// Add the request to our list
			rs.getRecordedRequests().add(rr);

			// Set the sequence
			rr.setSequence(rs.getRecordedRequests().size());
		}

		// return the recorded request object
		return rr;
	}

	protected List<NameValueBean> getParameters(HttpServletRequest request) {
		List<NameValueBean> parameters = new ArrayList<NameValueBean>();
		Map<?, ?> parameterMap = request.getParameterMap();
		for (Map.Entry<?, ?> pair : parameterMap.entrySet()) {
			String key = (String) pair.getKey();
			String[] values = (String[]) pair.getValue();
			NameValueBean bean = new NameValueBean();
			bean.setName(key);
			bean.setValues(values);
			parameters.add(bean);
		}
		Collections.sort(parameters);
		return parameters;
	}

	protected RecordedRequest getRecordedRequest(HttpServletRequest request, List<NameValueBean> parameters) {
		// Create and populate a RecordedRequest object
		RecordedRequest rr = new RecordedRequest();
		rr.setPath(request.getRequestURL() + "");
		rr.setParameters(parameters);
		return rr;
	}

}
