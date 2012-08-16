package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 6/7/12
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CourseOfferingCodeGenerator {
    String generateActivityOfferingCode(List<ActivityOfferingInfo> existingActivityOfferings);
    String generateCourseOfferingInternalCode(List<CourseOfferingInfo> existingCourseOfferings);
}
