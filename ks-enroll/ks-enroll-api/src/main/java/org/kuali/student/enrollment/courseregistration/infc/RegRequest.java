package org.kuali.student.enrollment.courseregistration.infc;

import java.util.List;

import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * The request made to the service from the application to register for
 * course(s). The attributes of this entity include requester (which will be the
 * student in most cases), request id and term. Each of the reg group the
 * students want to register would be a registration request item in the Request
 * object. For every transactional operation from the application, a new
 * Registration request is created.
 * 
 * @author Kuali Student Team (sambit)
 */
public interface RegRequest extends IdEntity {

    /**
     * The type of the overall registration request.
     * 
     * @see org.kuali.student.r2.common.infc.HasType#getTypeKey()
     */
    @Override
    public String getTypeKey();

    /**
     * Represents the state of the overall registration request.
     * 
     * @see org.kuali.student.r2.common.infc.HasState#getStateKey()
     */
    @Override
    public String getStateKey();

    /**
     * Returns the id of the person who requested the course registration. Most
     * of the times it would be a student but it could also be an admin or
     * faculty making a request on behalf of the student
     * 
     * @return
     */
    public String getRequestorId();

    /**
     * Returns the key of the {@link Term} of the registration request
     * 
     * @return
     */
    public String getTermId();

    /**
     * Returns a list of {@link RegRequestItem} that the RegRequest is composed
     * of.
     * 
     * @return
     */
    public List<? extends RegRequestItem> getRegRequestItems();

}
