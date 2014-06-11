package org.kuali.student.enrollment.class2.scheduleofclasses.service;


import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.CourseOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.sort.KSComparatorChain;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.SOCRequisiteWrapper;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: vgadiyak
 * Date: 9/12/12
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ScheduleOfClassesViewHelperService extends CourseOfferingManagementViewHelperService {

    public void loadCourseOfferingsByTermAndCourseCode (String termId, String courseCode, ScheduleOfClassesSearchForm form) throws Exception;

    public void loadCourseOfferingsByTermAndInstructor(String termId, String instructorId, String instructorName, ScheduleOfClassesSearchForm form) throws Exception;

    public void loadCourseOfferingsByTermAndDepartment(String termId, String organizationId, String organizationName, ScheduleOfClassesSearchForm form) throws Exception;

    /**
     * This method will populate the form object with CourseOfferingDisplay Objects. It will do a search on the Course Offering Titile OR Description.
     *
     * In addition to the ORing the title has an automatically applied like such that: like "title%"
     * which is ORed with like %description%
     *
     *
     * @param termId
     * @param titleOrDescription
     * @param form
     * @throws Exception
     */
    public void loadCourseOfferingsByTitleAndDescription(String termId, String titleOrDescription, ScheduleOfClassesSearchForm form) throws Exception;

    public SOCRequisiteWrapper retrieveRequisites(String coId, List<ActivityOfferingWrapper> activityOfferingWrapperList);

    public String getTermStartEndDate(TermInfo term);

    public void sortActivityOfferings(ScheduleOfClassesSearchForm form,CourseOfferingDisplayWrapper coWrapper);

    public void sortRegGroups(List<RegistrationGroupWrapper> regGroupWrappers);

    public KSComparatorChain getActivityComparatorChain();

    public List<String> getAOStateFilter();

    public List<String> getRegGroupStateFilter();

}
