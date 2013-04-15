package org.kuali.student.lum.conversion.util.factory;

import org.kuali.student.r2.lum.course.dto.CourseInfo;

public class CourseInfoFactory {
    
    public static CourseInfo newInstance() {
        CourseInfo courseInfo = new CourseInfo(null);
        return courseInfo;
    }

}

