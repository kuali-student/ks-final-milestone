package org.kuali.student.psrg.web;

/**
 * 
 *
 */
public interface RequestRecorderFilterListener {
	public RecordedRequest onBeforeDoFilter(HttpServletFilterContext context);

	public void onAfterDoFilter(HttpServletFilterContext context, RecordedRequest request);
}
