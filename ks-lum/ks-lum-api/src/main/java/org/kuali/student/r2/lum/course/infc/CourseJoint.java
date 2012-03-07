package org.kuali.student.r2.lum.course.infc;

import org.kuali.student.r2.common.infc.Entity;

/**
 * * Information about course joints.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface CourseJoint  extends Entity{
    /**
     * 
     */
    public String getCourseNumberSuffix();

    /**
     * Abbreviated name of the Course
     */
    public String getCourseTitle();

    /**
     * The Study Subject Area is used to identify the area of study associated
     * with the credit course. It may be a general study area (e.g. Chemistry)
     * or very specific (e.g. Naval Architecture).
     */
    public String getSubjectArea();

    /**
     * Unique identifier for a Course.
     */
    public String getCourseId();

    /**
     * Unique identifier for a Course Joints.
     */
    public String getRelationId();
}
