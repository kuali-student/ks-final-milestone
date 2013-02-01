package org.kuali.student.myplan.plan.util;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 1/31/13
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AtpHelperService {

    /**
     * Query the Academic Calendar Service, determine the current ATP, and
     * return the ID.
     *
     * @return The ID of the current ATP.
     */
    public String getCurrentAtpId();

    /**
     * Query the Academic Calendar Service for terms that have offering's
     * published, determine the last ATP, and return its ID.
     *
     * @return The ID of the last scheduled ATP.
     * @throws RuntimeException
     *             if the query fails or if the return data set doesn't make
     *             sense.
     */
    public String getLastScheduledAtpId();

    /**
     * Gets the ATP ID of the first ATP in the current academic year.
     */

    public String getFirstAtpIdOfAcademicYear(String atpId);

    /**
     * Returns an String[] {term, year} given an ATP ID.
     */
    public String[] atpIdToTermAndYear(String atpId);

    /**
     * Converts an ATP ID to a Term and Year ... "kuali.uw.atp.1991.1" ->
     * {"Autumn", "1991"}
     *
     * @return A String array containing a term and year.
     */
    public String[] atpIdToTermNameAndYear(String atpId);

    public String getAtpIdFromTermAndYear(String term, String year);

    public String getAtpFromNumTermAndYear(String term, String year) ;

    /**
     * Gets term name as "Spring 2012" given an ATP ID.
     *
     * @return
     */
    public String atpIdToTermName(String atpId);

    /**
     * Returns true if an ATP is considered present or greater in the context of
     * WHAT? Otherwise, false.
     *
     * @param atpId
     * @return
     */
    public boolean isAtpSetToPlanning(String atpId);

    /**
     * Returns  if an ATP is considered present or greater in the context of
     * WHAT? Otherwise, false.
     *
     * @param atpId
     * @return
     */
    public boolean isAtpCompletedTerm(String atpId);

    public boolean isAtpIdFormatValid(String atpId);

    public void addServiceError(String propertyName);
}
