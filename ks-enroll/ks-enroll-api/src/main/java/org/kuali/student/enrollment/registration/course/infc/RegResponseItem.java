package org.kuali.student.enrollment.registration.course.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.HasAttributesAndMeta;

/**
 * A more granular status object than the Registration response which gives the
 * response status of the individual registration item, i.e., a course reg
 * group. It has a reference to the Course Registration in case of a successful
 * registration. The response contains status such as SUCCESS or FAILURE and
 * then necessary messages, warning or errors. The state of the response in case
 * of a success can be registered, waitlisted, or holdlisted.
 * 
 * @author Kuali Student Team (sambit)
 */
public interface RegResponseItem extends HasAttributesAndMeta {
    /**
     * This method gets the item reg message displaying it to the user
     * 
     * @return
     */
    public List<String> getItemRegMessages();

    /**
     * The status of registration for the individual reg group.
     * 
     * @return
     */
    public String getItemRegStatus();

    /**
     * Any warnings that need to be shown for the reg group registration
     * attempt.
     * 
     * @return
     */
    public List<String> getItemRegWarnings();

    /**
     * Returns any error specific to the reg group registration process.
     * 
     * @return
     */
    public List<String> getItemRegErrors();

    /**
     * Returns the course registration id that results from a successful
     * registration transaction
     * 
     * @return
     */
    public String getCourseRegistrationId();
}
