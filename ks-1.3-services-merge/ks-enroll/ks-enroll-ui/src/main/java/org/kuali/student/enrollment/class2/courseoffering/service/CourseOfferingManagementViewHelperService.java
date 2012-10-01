package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.List;

public interface CourseOfferingManagementViewHelperService {
    /**
     *
     * @param termCode Each institution uses a code to represent a term.  At UW, the code appears to be three letters
     *                 followed by a 4-digit year, e.g., FAL2011, WIN2011, etc.
     * @return List of terms which match the term code (should be a list of one TermInfo)
     * @throws Exception
     */
    public List<TermInfo> findTermByTermCode(String termCode) throws Exception;
    public List<CourseOfferingInfo> findCourseOfferingsByTermAndCourseOfferingCode (String termCode, String courseOfferingCode, CourseOfferingManagementForm form) throws Exception;
    public void loadCourseOfferingsByTermAndSubjectCode (String termId, String subjectCode, CourseOfferingManagementForm form) throws Exception;
    public void loadActivityOfferingsByCourseOffering (CourseOfferingInfo theCourseOfferingInfo, CourseOfferingManagementForm form) throws Exception;

    public void createActivityOfferings(String formatOfferingId,String activityId,int noOfActivityOfferings, CourseOfferingManagementForm form);
}
