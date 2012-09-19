package org.kuali.student.enrollment.class2.scheduleofclasses.service;

import org.kuali.student.enrollment.class2.scheduleofclasses.dto.ActivityOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vgadiyak
 * Date: 9/12/12
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ScheduleOfClassesViewHelperService {

    public void loadCourseOfferingsByTermAndCourseCode (String termId, String courseCode, ScheduleOfClassesSearchForm form) throws Exception;

    public void loadCourseOfferingsByTermAndInstructor(String termId, String instructorId, ScheduleOfClassesSearchForm form) throws Exception;

    public void loadActivityOfferingsByCourseOfferingId(String courseOfferingId, ScheduleOfClassesSearchForm form) throws Exception;

}
