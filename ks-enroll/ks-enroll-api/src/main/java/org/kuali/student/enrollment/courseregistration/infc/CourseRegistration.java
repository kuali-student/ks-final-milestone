package org.kuali.student.enrollment.courseregistration.infc;

import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.infc.Relationship;

/**
 * Course Registration is the consolidated view of the details of a
 * student's relation with the course, which includes fields like
 * CourseOffering, the particular RegistrationGroupInfo that the
 * student registered through, the number of credits opted, and the
 * grading option chosen by the student.
 *
 * The CourseRegistration is created only upon the successful
 * registration of the student into the course, and not after waitlist
 * or any other type of relation with the course.
 */

public interface CourseRegistration 
    extends Relationship {

    /**
     * Returns the Course offering id for this course registration.
     * 
     * @name Course Offering Id
     * @readOnly
     * @required
     */
    public String getCourseOfferingId();

    /**
     * Returns the student id for the course registration.
     * 
     * @name Student Id
     * @readOnly
     * @required
     */
    public String getStudentId();

    /**
     * Returns the credit count for this registration.
     * 
     * @return
     */
    public String getCredits();

    /**
     * Returns the grading option
     * 
     * @return
     */
    public String getGradingOptionKey();
}
