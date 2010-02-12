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
import org.kuali.rice.core.config.ConfigContext;

import static org.kuali.student.common.ui.server.gwt.Constants.*;

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
	boolean debugMode = "true".equalsIgnoreCase(ConfigContext.getCurrentContextConfig().getProperty(HTTP_REQUEST_DEBUG_MODE));

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
		log.debug("Request Count: " + (++sequence));
		HttpServletRequest request = (HttpServletRequest) req;
		RecordedRequest rr = onBeforeDoFilter(request);
		chain.doFilter(request, response);
		onAfterDoFilter(request, rr);
	}

	/**
	 * Servlet container is shutting down
	 */
	public void destroy() {
		log.info("System Shutdown Initiated: " + System.currentTimeMillis() + " Available Processors: " + runtime.availableProcessors() + " Maximum Memory: " + runtime.maxMemory());
	}

	/**
	 * Record information about this HttpRequest.<br>
	 */
	public void onAfterDoFilter(HttpServletRequest request, RecordedRequest rr) {
		if (!debugMode) {
			return;
		}
		rr.setFinishTime(new Date());
		handleRPC(request, rr);
	}

	/**
	 * Detect if this is an GWT RPC call. If so, record the RPC data
	 */
	protected void handleRPC(HttpServletRequest request, RecordedRequest rr) {
		if (request.getAttribute(RPC_METHOD_KEY) == null) {
			return;
		}
		List<NameValuesBean> parameters = new ArrayList<NameValuesBean>();
		parameters.add(getNameValuesBean(request, RPC_METHOD_KEY));
		parameters.add(getNameValuesBean(request, RPC_PARAMETERS_KEY));
		rr.setParameters(parameters);
	}

	/**
	 * Convert a request attribute to a NameValuesBean
	 */
	protected NameValuesBean getNameValuesBean(HttpServletRequest request, String name) {
		String parameters = (String) request.getAttribute(name);
		NameValuesBean bean = new NameValuesBean();
		bean.setName(name);
		bean.setValues(new String[] { parameters });
		return bean;
	}

	/**
	 * Record information about this HttpRequest.<br>
	 */
	public RecordedRequest onBeforeDoFilter(HttpServletRequest request) {
		if (!debugMode) {
			return null;
		}
		HttpSession session = request.getSession(true);
		RecordedSession rs = (RecordedSession) session.getAttribute(SESSION_RECORDER_KEY);
		if (rs == null) {
			log.info("Recording a new HTTP session: " + session.getId());
			rs = new RecordedSession();
			session.setAttribute(SESSION_RECORDER_KEY, rs);
		}

		// Get a recorded request object
		RecordedRequest rr = getRecordedRequest(request);

		/**
		 * GWT can spew lots of asynchronous requests. Synchronize on the
		 * RecordedSession object to insure that the modification to the list of
		 * recorded requests and the setting of the sequence number is thread
		 * safe.
		 */
		synchronized (rs) {
			// Add the request to our list
			rs.getRecordedRequests().add(rr);

			// Set the sequence
			rr.setSequence(rs.getRecordedRequests().size());
		}

		// return the recorded request object
		return rr;
	}

	protected List<NameValuesBean> getParameters(HttpServletRequest request) {
		List<NameValuesBean> parameters = new ArrayList<NameValuesBean>();
		Map<?, ?> parameterMap = request.getParameterMap();
		for (Map.Entry<?, ?> pair : parameterMap.entrySet()) {
			String key = (String) pair.getKey();
			String[] values = (String[]) pair.getValue();
			NameValuesBean bean = new NameValuesBean();
			bean.setName(key);
			bean.setValues(values);
			parameters.add(bean);
		}
		Collections.sort(parameters);
		return parameters;
	}

	protected RecordedRequest getRecordedRequest(HttpServletRequest request) {
		// Copy the parameter list
		List<NameValuesBean> parameters = getParameters(request);

		// Create and populate a RecordedRequest object
		RecordedRequest rr = new RecordedRequest();
		rr.setStartTime(new Date());
		rr.setPath(request.getRequestURL() + "");
		rr.setParameters(parameters);
		return rr;
	}

}
