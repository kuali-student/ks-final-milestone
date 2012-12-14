package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.List;

/**
 * Provides a way to customize how the ActivityOfferingCode is generated.
 * 
 * @author Kuali Student Team
 */
public interface CourseOfferingCodeGenerator {
    String generateActivityOfferingCode(String courseOfferingCode, List<ActivityOfferingInfo> existingActivityOfferings);
    String generateCourseOfferingInternalCode(List<CourseOfferingInfo> existingCourseOfferings);
}
