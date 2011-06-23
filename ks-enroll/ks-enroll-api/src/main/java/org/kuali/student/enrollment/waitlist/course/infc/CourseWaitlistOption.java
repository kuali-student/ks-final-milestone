package org.kuali.student.enrollment.waitlist.course.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.TimeAmount;

/**
 * The CourseWaitlistOption represents a single waitlist for a reg group in a
 * course. It contains the reg group id for which this particular option is for
 * and the clearing strategy, i.e., the method how this waitlist will be
 * cleared. If the course has just one waitlist clearing methods for all
 * reg-groups, then the number of CourseWailtlistOptions will be as many as the
 * reg groups in the course. If a course has just one reg group, only one of
 * this entity has to be created.
 * 
 * @author Kuali Student Team (sambit)
 */
public interface CourseWaitlistOption extends IdEntity {

    /**
     * The reg group for the waitlist option, a waitlist can be created for a
     * particular reg group the students try to enroll in.
     * 
     * @return
     */
    public String getRegGroupId();

    /**
     * Return one of the course waitlist clearing strategies - automatic,
     * semi-automatic or manual for this particular reg-group.
     * 
     * @return
     */
    public String getClearingStrategy();
    

    /**
     * Returns if check-in by a student on the list is required by this
     * waitlist.
     * 
     * @return
     */
    public Boolean getCheckInRequired();
    
    /**
     * Returns the check in frequency for the Waitlist. A Waitlist might have a
     * check in frequency requirement for all the students in the Waitlist.
     * 
     * @return
     */
    public TimeAmount getCheckInFrequency();


}
