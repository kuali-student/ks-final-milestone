package org.kuali.student.psu.web;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * POJO with ServletFilter context information (request,response, filterChain)
 */
public class HttpServletFilterContext {
	HttpServletRequest request;
	HttpServletResponse response;
	FilterChain chain;

	public HttpServletFilterContext() {
		super();
	}

	public HttpServletFilterContext(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
		super();
		this.request = request;
		this.response = response;
		this.chain = filterChain;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public FilterChain getChain() {
		return chain;
	}

	public void setChain(FilterChain filterChain) {
		this.chain = filterChain;
	}
}
