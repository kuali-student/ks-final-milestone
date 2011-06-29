package org.kuali.student.enrollment.registration.course.infc;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Represents a single reg group in the request to register. This request is
 * also used to request waitlist or drop a course. It has transaction type of
 * either ADD, DROP, SWAP or UPDATE to represent adding (waitlisting,
 * holdlisting), dropping courses or persisting an item from the reg cart with
 * changes to it. It has other options like grading and credit options that a
 * student can specify while registering. There will be options like
 * okToWaitlist, okToHoldList, okToExceptionList which will help specify if the
 * student wants to go to a waitlist/holdlist in case there's no seat
 * availability for the course. A request to waitlist a course should always
 * have okToWaitlist set to true, same for hold or exception lists. 
 * <P>
 * ************POSSIBLE SCENARIOS*******************************
 * 
 * 1. Register for course - {@link RegRequestItem} Type is ADD ,
 *  newRegGroupId is the reg group to be registered for 
 *  
 * 2. Register for course but waitlist if seat not available OR
 * waitlist for course - same as above and okToWaitlist is true
 * 
 * 3. Swap between reg group within same course offering - Type is swap,
 * both new and existing reg group ids populated. new is to be the one 
 * replaced with the old one. Reg groups are in the same course offering.
 * 
 * 4.Save reg request - Type is UPDATE and newRegGroupId is populated 
 * 
 * 
 * @author Kuali Student Team (sambit)
 */
public interface RegRequestItem extends IdEntity {
    /**
     * Returns the id of the RegGroup for this item. This is populated for ADD,
     * DROP, UPDATE and SWAP types of RegRequestItem
     * 
     * @return
     */
    public String getNewRegGroupId();

    /**
     * Returns the existing reg group id. The existing reg group field is
     * populated if we have DROP or SWAP types of Request item.
     * 
     * @return
     */
    public String getExistingRegGroupId();

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
