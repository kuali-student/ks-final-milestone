package org.kuali.student.ap.framework.context;

/**
 * Provides access to common ATP functionality.
 */
public interface AtpHelper {

    /**
     * Query the Academic Calendar Service, determine the current ATP, and
     * return the ID.
     *
     * @return The ID of the current ATP.
     */
    String getCurrentAtpId();

    /**
     * Query the Academic Calendar Service for terms that have offering's
     * published, determine the last ATP, and return its ID.
     *
     * @return The ID of the last scheduled ATP.
     * @throws RuntimeException
     *             if the query fails or if the return data set doesn't make
     *             sense.
     */
    String getLastScheduledAtpId();

    /**
     * Gets the ATP ID of the first ATP in the current academic year.
     */
    String getFirstAtpIdOfAcademicYear(String atpId);

    /**
     * Returns an String[] {term, year} given an ATP ID.
     */
    String[] atpIdToTermAndYear(String atpId);

    /**
     * Converts an ATP ID to a Term and Year ... "kuali.uw.atp.1991.1" ->
     * {"Autumn", "1991"}
     *
     * @return A String array containing a term and year.
     */
    String[] atpIdToTermNameAndYear(String atpId);

    String getAtpIdFromTermAndYear(String term, String year);

    String getAtpFromNumTermAndYear(String term, String year) ;

    /**
     * Gets term name as "Spring 2012" given an ATP ID.
     *
     * @return
     */
    String atpIdToTermName(String atpId);

    /**
     * Returns true if an ATP is considered present or greater in the context of
     * WHAT? Otherwise, false.
     *
     * @param atpId
     * @return
     */
    boolean isAtpSetToPlanning(String atpId);

    /**
     * Returns  if an ATP is considered present or greater in the context of
     * WHAT? Otherwise, false.
     *
     * @param atpId
     * @return
     */
    boolean isAtpCompletedTerm(String atpId);

    boolean isAtpIdFormatValid(String atpId);

    void addServiceError(String propertyName);
}
