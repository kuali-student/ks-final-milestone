package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.List;
import java.util.Map;

/**
 * Provides a way to customize how the ActivityOfferingCode is generated.
 * 
 * @author Kuali Student Team
 */
public interface CourseOfferingCodeGenerator {
    String generateActivityOfferingCode(String courseOfferingCode, Map<String, Object> generationProperties);
    String generateCourseOfferingInternalCode(List<CourseOfferingInfo> existingCourseOfferings);
}
