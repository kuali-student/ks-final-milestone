package org.kuali.student.ap.framework.context;

import org.kuali.student.enrollment.acal.dto.TermInfo;

import java.util.List;
import java.util.Map;

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
     * Adds an error to the page
     */
    void addServiceError(String propertyName);

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
     *
     * @param yearTerm - Year and term storage object
     * @return atpId created form object information
     */
    public String getAtpId(YearTerm yearTerm);

    /**
     *
     * @param year - String representation of year
     * @param term - String representation of term
     * @return atpId created from year and term
     */
    public String getAtpId(String year, String term);

    /**
     *
     * @param year - Integer representation of year
     * @param term - Term index
     * @return atpId created from year and term index
     */
    public String getAtpId(int year, int term);

    /**
     *
     * @param year - Integer representation of year
     * @param Term - String representation of term
     * @return atpId created from year and term
     */
    public String getAtpId(int year, String Term);

    /**
     *
     * @param atpId - Id of term to check
     * @return true if the term is considered completed
     */
    boolean isCompletedTerm(String atpId);

    /**
     *
     * @param atpId - Id of term
     * @return true if the id is valid
     */
    boolean validateAtpId(String atpId);

    /**
     *
     * @param atpId - Id of term
     * @return true if the id is of a valid term
     */
    boolean validateAtp(String atpId);

    /**
     *
     * @param atpId - Id of a term
     * @return YearTerm holding information for the term
     */
    YearTerm getYearTerm(String atpId);

    public void setTermMap(Map<String,Integer> termMap);

    public Map<String,Integer> getTermMap();

    public void setAtpTypeMap(Map<Integer,String> termMap);

    public Map<Integer,String> getAtpTypeMap();

    public void setTypeMonthDayMap(Map<Integer,String> termMap);

    public Map<Integer,String> getTypeMonthDayMap();


}
