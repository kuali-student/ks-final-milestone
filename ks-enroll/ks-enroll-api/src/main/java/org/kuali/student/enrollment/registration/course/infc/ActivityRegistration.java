package org.kuali.student.enrollment.registration.course.infc;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * An ActivityRegistration represents the activities in the course the student
 * has registered for. For a given course there will be multiple activity
 * offerings. For each course registration there will be at least one activity
 * registration.
 * 
 * @author Kuali Student Team (sambit)
 */
public interface ActivityRegistration extends Relationship {

    /**
     * Returns the activity offering id for the activity registration.
     * 
     * @return
     */
    public String getActivityOfferingId();

    /**
     * This method returns the student id for the Activity registration.
     * 
     * @return
     */
    public String getStudentId();


}
