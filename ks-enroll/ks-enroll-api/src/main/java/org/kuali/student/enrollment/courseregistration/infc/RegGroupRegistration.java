package org.kuali.student.enrollment.courseregistration.infc;

import java.util.List;

import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * A reg group registration returns the
 * 
 * @author Kuali Student Team (sambit)
 */
public interface RegGroupRegistration extends Relationship {

    /**
     * Returns the reg group id for the RegGroupRegistration.
     * 
     * @return
     */
    public RegistrationGroup getRegistrationGroup();
    /**
     * 
     * @return
     */
    public List<? extends ActivityRegistration> getActivityRegistrations();

    /**
     * This method returns the student id for the RegGroupRegistration.
     * 
     * @return
     */
    public String getStudentId();

}
