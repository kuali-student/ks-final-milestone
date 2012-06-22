package org.kuali.student.enrollment.courseregistration.infc;

import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
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
    public ActivityOffering getActivityOffering();

    /**
     * Student id for this Activity Registration.
     * 
     * @return
     */
    public String getStudentId();

}
