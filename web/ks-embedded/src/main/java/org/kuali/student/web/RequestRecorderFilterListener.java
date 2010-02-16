package org.kuali.student.web;

/**
 * 
 *
 */
public interface RequestRecorderFilterListener {
	public RecordedRequest onBeforeDoFilter(HttpServletFilterContext context);

	public void onAfterDoFilter(HttpServletFilterContext context, RecordedRequest request);
}
