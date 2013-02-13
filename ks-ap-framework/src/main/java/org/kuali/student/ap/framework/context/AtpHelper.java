package org.kuali.student.ap.framework.context;

import org.kuali.student.enrollment.acal.dto.TermInfo;

import java.util.List;

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
     * Converts an ATP ID to a Term and Year ... "kuali.atp.1991.1" ->
     * {"Autumn", "1991"}
     *
     * @return A String array containing a term and year.
     */
    String[] atpIdToTermNameAndYear(String atpId);

    /**
     * Converts the term and year into a atp id ... {"Autumn", "1991"} -> "19914"
     *
     * @return A string holding the atp id.
     */
    String getAtpIdFromTermAndYear(String term, String year);

    /**
     * Converts the term and year into a atp id ... {"1", "1991"} -> "19911"
     *
     * @return
     */
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
     * Returns true if an ATP is considered present or greater in the context of
     * the current term's atp. Otherwise, false.
     *
     * @param atpId
     * @return
     */
    boolean isAtpCompletedTerm(String atpId);

    /**
     * Checks if the atp id passed in is valid to the form being sent in.
     *
     * @return True if the atp passes validation, false otherwise
     */
    boolean isAtpIdFormatValid(String atpId);

    /**
     * Adds an error to the page
     */
    void addServiceError(String propertyName);

    /**
     * Converts Kuali ATP ids into a YearTerm object.
     *
     * eg "kuali.atp.2012.1" becomes year = 2012, term = 1
     *
     * @param atp
     * @return
     */
    public YearTerm atpToYearTerm(String atp) ;

    /**
     * Converts quarter string into a YearTerm object.
     *
     * eg "Winter 2012" becomes year = 2012, term = 1
     *
     * @param text
     * @return
     */
    public YearTerm termToYearTerm(String text);

    /**
     * Converts quarter string into a YearTerm object.
     *
     * eg "Winter","2012" becomes year = 2012, term = 1
     *
     * @param
     * @return
     */
    public YearTerm quarterYearToYearTerm(String quarter, String year);

    /**
     * Determines whether a course is in a specific term.
     *
     * @param atp
     * @param course
     * @return
     */
    public boolean isCourseOfferedInTerm(String atp, String course);

    /**
     * Gets a list of published terms.
     *
     * @return
     */
    public List<String> getPublishedTerms();

    /**
     * Gets the first term of the published terms
     * @return
     */
    public String getFirstPlanTerm();

    /**
     * Returns whether the atp id passed in exists or not
     * @param atpId
     * @return
     */
    public boolean doesAtpExist(String atpId);

    /**
     * Creates a atp id using the actual date and then returns a term info with the id filled in.
     * @return
     */
    List<TermInfo> populateAtpIdFromCalender();
}
