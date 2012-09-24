package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.List;

/**
 * Planning to use this to rollover one course offering for a term
 * User: Charles
 */
public interface DiagnoseRolloverViewHelperService {
    public TermInfo searchTermByTermCode(String termCode) throws Exception;
    public boolean termHasCourseOfferings(TermInfo termInfo) throws Exception;
    public boolean termHasSoc(TermInfo termInfo) throws Exception;
    public CourseOfferingInfo getCourseOfferingInfo(String termId, String courseOfferingCode) throws Exception;
}
