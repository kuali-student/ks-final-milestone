package org.kuali.student.ap.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.kuali.student.ap.audit.service.DegreeAuditConstants;
import org.kuali.student.ap.framework.context.CourseSearchConstants;

public class ArmKsapFilter implements Filter {

	@Override
	public void init(FilterConfig fc) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse resp,
			final FilterChain fc) throws IOException, ServletException {

		// These attributes checks prevent NPE in UW-specific logic within My
		// Plan controllers.
		req.setAttribute(CourseSearchConstants.IS_ACADEMIC_CALENDER_SERVICE_UP,
				true);
		req.setAttribute(CourseSearchConstants.IS_COURSE_OFFERING_SERVICE_UP,
				true);
		req.setAttribute(CourseSearchConstants.IS_ACADEMIC_RECORD_SERVICE_UP,
				true);
		req.setAttribute(DegreeAuditConstants.IS_AUDIT_SERVICE_UP, true);

		fc.doFilter(req, resp);
		
	}

}
