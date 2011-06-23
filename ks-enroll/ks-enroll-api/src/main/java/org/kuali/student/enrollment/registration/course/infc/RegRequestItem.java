package org.kuali.student.enrollment.registration.course.infc;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Represents a single reg group in the registration request. Allows students to
 * specify options such as grading options. It also has a transaction type of
 * either ADD, DROP, or UPDATE to represent adding, dropping courses or
 * persisting an item from the reg cart with changes to it. It has other options
 * like grading and credit options that a student can specify while registering.
 * There will be options like okToWaitlist, okToHoldList, okToExceptionList
 * which will help specify if the student wants to go to a waitlist/holdlist in
 * case there's no seat availability for the course.
 * 
 * @author Kuali Student Team (sambit)
 */
public interface RegRequestItem extends IdEntity {
    /**
     * Returns the id of the RegGroup for this item.
     * 
     * @return
     */
    public String getRegGroupId();

    /**
     * If the course is full and there is a waitlist, is it okay to be placed in
     * the waitlist for the course
     */
    public Boolean getOkToWaitlist();

    /**
     * If the course is full and there is a hold list, is it okay to be placed
     * in the hold list for the course
     * 
     * @return
     */
    public Boolean getOkToHoldList();

    /**
     * If the student does not meet one of the requirements for the course but
     * there is a list for such students because the requirement ends at a
     * future date, is it okay to be placed in such a list.
     * 
     * @return
     */
    public Boolean getOkToExceptionList();

    /**
     * This method returns the RegResponseItem {@link RegResponseItem} for this
     * request in case the request has already been submitted
     */
    public RegResponseItem getRegResponseItem();

    /**
     * Specify the preferred grading option for the course e.g. pass/fail since
     * the course allows to specify such options
     * 
     * @return
     */
    public String getGradingOption();

    /**
     * Specify the preferred credit option e.g credit/ no-credit for the course
     * because the course.
     * 
     * @return
     */
    public String getCreditOption();

}
