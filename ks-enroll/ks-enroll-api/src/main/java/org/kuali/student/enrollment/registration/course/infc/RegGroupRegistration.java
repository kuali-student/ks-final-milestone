package org.kuali.student.enrollment.registration.course.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.Relationship;

/**
 * 
 * A reg group registration returns the  
 * 
 * @author Kuali Student Team (sambit)
 *
 */
public interface RegGroupRegistration  extends Relationship{

    
    /**
     * Returns the reg group id for the RegGroupRegistration.
     * 
     * @return
     */
    public String getRegGroupId();

    /**
     * This method returns the student id for the RegGroupRegistration.
     * 
     * @return
     */
    public String getStudentId();

    /**
     * The course registration which this reg-group registration is a part of.
     * 
     * @return
     */
    public String getCourseRegistrationId();
}
