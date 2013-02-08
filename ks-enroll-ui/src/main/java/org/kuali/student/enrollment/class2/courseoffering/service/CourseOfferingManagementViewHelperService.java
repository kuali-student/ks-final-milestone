package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.List;

public interface CourseOfferingManagementViewHelperService extends CO_AO_RG_ViewHelperService{

    public void populateTerm(CourseOfferingManagementForm form) throws Exception;

    public void loadCourseOfferingsByTermAndCourseCode(String termId, String courseCode, CourseOfferingManagementForm form) throws Exception;

    public void loadCourseOfferingsByTermAndSubjectCode(String termId, String subjectCode, CourseOfferingManagementForm form) throws Exception;

    public void loadActivityOfferingsByCourseOffering (CourseOfferingInfo theCourseOfferingInfo, CourseOfferingManagementForm form) throws Exception;

    public void createActivityOfferings(String formatOfferingId,String activityId,int noOfActivityOfferings, CourseOfferingManagementForm form);

    public void changeActivityOfferingsState(List<ActivityOfferingWrapper> aoList, CourseOfferingInfo courseOfferingInfo, String selectedAction) throws Exception;

    public void markCourseOfferingsForScheduling(List<CourseOfferingListSectionWrapper> coWrappers) throws Exception;

    public void loadPreviousAndNextCourseOffering(CourseOfferingManagementForm form);

    public List<ActivityOfferingWrapper> getActivityOfferingsByCourseOfferingId (String courseOfferingId, CourseOfferingManagementForm form) throws Exception;

    public void approveCourseOfferings(CourseOfferingManagementForm form) throws Exception;
    public void deleteCourseOfferings(CourseOfferingManagementForm form) throws Exception;
    public void approveActivityOfferings(CourseOfferingManagementForm form) throws Exception;
    public void draftActivityOfferings(CourseOfferingManagementForm form) throws Exception;

}
