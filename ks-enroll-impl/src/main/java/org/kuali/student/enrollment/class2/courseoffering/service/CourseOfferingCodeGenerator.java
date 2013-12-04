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

    public static String COURSE_OFFERING_CODE_KEY = "courseOfferingCode";
    public static String ACTIVITY_OFFERING_CODE_LIST_KEY = "activityOfferingCodes";

    /**
     *
     * @param generatorPropertiesMap  A map containing the properties needed to generate the codes
     * @return
     */
    String generateActivityOfferingCode(Map<String, Object> generatorPropertiesMap);
    String generateCourseOfferingInternalCode(List<CourseOfferingInfo> existingCourseOfferings);
}
