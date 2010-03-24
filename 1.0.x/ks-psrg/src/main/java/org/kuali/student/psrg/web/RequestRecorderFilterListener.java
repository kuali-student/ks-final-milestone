package org.kuali.student.psrg.web;

/**
 * 
 *
 */
public interface RequestRecorderFilterListener {
	public void onBeforeDoFilter(HttpServletFilterContext context);

	public void onAfterDoFilter(HttpServletFilterContext context);
}
