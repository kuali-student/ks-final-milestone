package org.kuali.student.enrollment.registration.course.infc;

import java.util.List;

import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * The request made to the service from the application to register for
 * course(s). The attributes of this entity include students, requester (which
 * will be the student in most cases), request id and term. Each of the reg
 * group the students want to register would be a registration request item in
 * the Request object. For every transactional operation from the application, a
 * new Registration request is created.
 * 
 * @author Kuali Student Team (sambit)
 */
public interface RegRequest extends IdEntity {

    /**
     * Returns the id of the person who requested the course registration. Most
     * of the times it would be a student but it could also be an admin or
     * faculty making a request on behalf of the student
     * 
     * @return
     */
    public String getRequestorId();

    /**
     * Return the id of the student to be registered
     * 
     * @return
     */

    public String getStudentId();

    /**
     * Returns the key of the {@link Term} of the registration request
     * 
     * @return
     */
    public String getTermKey();

    /**
     * Returns a list of {@link RegRequestItem} that the RegRequest is composed
     * of.
     * 
     * @return
     */
    public List<RegRequestItem> getRegRequestItems();

    /**
     * Returns the {@link RegResponse} if the request is already submitted.
     * Returns null if the request is not processed yet.
     * 
     * @return
     */
    public RegResponse getRegResponse();
}
