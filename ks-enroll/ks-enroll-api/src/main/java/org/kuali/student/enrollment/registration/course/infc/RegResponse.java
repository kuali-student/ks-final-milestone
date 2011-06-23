package org.kuali.student.enrollment.registration.course.infc;


import java.util.List;


import org.kuali.student.r2.common.infc.IdEntity;

public interface RegResponse extends IdEntity {
    /**
     * This method gets the item reg message displaying it to the user
     * 
     * @return
     */
    public List<String> getRegMessages();

    /**
     * The status of registration for the overall registration.
     * 
     * @return
     */
    public String getOverallRegStatus();

    /**
     * Any warnings that need to be shown for the registration
     * attempt.
     * 
     * @return
     */
    public List<String> getRegWarnings();

    /**
     * Returns any errors for the overall registration process.
     * 
     * @return
     */
    public List<String> getRegErrors();

}
