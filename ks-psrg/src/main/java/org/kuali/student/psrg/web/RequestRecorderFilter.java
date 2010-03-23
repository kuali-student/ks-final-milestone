package org.kuali.student.psrg.web;

import java.io.IOException;

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
 * Provides a hook for tracking HttpRequests
 */
public class RequestRecorderFilter implements Filter {

	private static final Log log = LogFactory.getLog(RequestRecorderFilter.class);
	Runtime runtime = Runtime.getRuntime();
	long sequence = 0;
	RequestRecorderFilterListener listener = new HttpRequestListenerImpl();

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
		log.info("Request Count: " + (++sequence));
		HttpServletFilterContext context = new HttpServletFilterContext((HttpServletRequest) request, (HttpServletResponse) response, chain);
		RecordedRequest rr = listener.onBeforeDoFilter(context);
		chain.doFilter(context.getRequest(), context.getResponse());
		listener.onAfterDoFilter(context, rr);
	}

	/**
	 * Servlet container is shutting down
	 */
	public void destroy() {
		log.info("System Shutdown Initiated: " + System.currentTimeMillis() + " Available Processors: " + runtime.availableProcessors() + " Maximum Memory: " + runtime.maxMemory());
	}

	public RequestRecorderFilterListener getListener() {
		return listener;
	}

	public void setListener(RequestRecorderFilterListener listener) {
		this.listener = listener;
	}

}
