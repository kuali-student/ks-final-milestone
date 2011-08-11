package org.kuali.student.enrollment.courseregistration.infc;

import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseregistration.dto.RegGroupRegistrationInfo;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.Relationship;

/**
 * 
 * This is a description of what this class does - sambit don't forget to fill this in. 
 * 
 * @author Kuali Student Team (sambit)
 *
 */
public interface CourseRegistration extends Relationship{
    
    /**
     * 
     * Returns the Course offering id for this course registration.
     * 
     * @return
     */
    public CourseOffering getCourseOffering();
    
    /**
     * 
     * Returns the student id for the course registration.
     * 
     * @return
     */
    public String getStudentId();
    
    
    /**
     * 
     * Returns the credit count for this registration
     * 
     * @return
     */
    public String getCreditCount();
    
    /**
     * 
     * Returns the grading option
     * 
     * @return
     */
    public String getGradingOption();
    
    /**
     * 
     * Returns the registration group associated with this course offering.
     * 
     * @return
     */
    public RegGroupRegistration getRegGroupRegistration();
    
}
