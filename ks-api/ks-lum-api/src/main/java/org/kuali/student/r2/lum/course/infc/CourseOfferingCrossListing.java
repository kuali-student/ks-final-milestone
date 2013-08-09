package org.kuali.student.r2.lum.course.infc;

import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.HasId;

/**
 * Created with IntelliJ IDEA.
 * User: mahtabme (Mezba Mahtab)
 * Date: 10/12/12
 * Time: 11:22 AM
 *
 * Represents a crosslisted information of a course offering.
 */
public interface CourseOfferingCrossListing extends HasId, HasAttributesAndMeta {

    /**
     * Gets the course offering code.
     */
    public String getCode();

    /**
     * Gets the subject area of the course offering.
     */
    public String getSubjectArea();

    /**
     * Gets the department of the course offering.
     */
    public String getDepartment();

    /**
     * The "extra" portion of the code, which usually corresponds with the most
     * detailed part of the number.
     */
    public String getCourseNumberSuffix();
}
