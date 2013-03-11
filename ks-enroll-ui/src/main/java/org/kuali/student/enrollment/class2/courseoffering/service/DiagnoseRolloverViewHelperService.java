package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.List;
import java.util.Map;

/**
 * Planning to use this to rollover one course offering for a term
 * User: Charles
 */
public interface DiagnoseRolloverViewHelperService {
    public TermInfo searchTermByTermCode(String termCode) throws Exception;
    public boolean termHasCourseOfferings(TermInfo termInfo) throws Exception;
    public boolean termHasACourseOffering(String termId, String courseOfferingCode) throws Exception;
    public boolean termHasSoc(TermInfo termInfo) throws Exception;
    public boolean deleteCourseOfferingInTerm(String courseOfferingCode, String termId) throws Exception;
    public Map<String, Object> rolloverCourseOfferingFromSourceTermToTargetTerm(String courseOfferingCode,
                                                                               String sourceTermCode,
                                                                               String targetTermCode)
            throws Exception;
    public CourseOfferingInfo getCourseOfferingInfo(String termId, String courseOfferingCode) throws Exception;
}
