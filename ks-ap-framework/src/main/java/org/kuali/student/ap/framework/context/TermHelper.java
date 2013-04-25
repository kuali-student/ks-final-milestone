package org.kuali.student.ap.framework.context;

import java.util.List;

import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;

/**
 * Provides access to common ATP functionality.
 */
public interface TermHelper {

	/**
	 * Look up a term by ATP ID.
	 * 
	 * @param atpId
	 *            The ATP ID for the term.
	 * @return The term corresponding to the given ATP ID.
	 */
	Term getTerm(String atpId);

	/**
	 * Look up a year/term by ATP ID.
	 * 
	 * @param atpId
	 *            The ATP ID for the term.
	 * @return The term corresponding to the given ATP ID.
	 */
	YearTerm getYearTerm(String atpId);

	/**
	 * Query the Academic Calendar Service, determine the current ATP, and
	 * return the ID.
	 * 
	 * @return The ID of the current ATP.
	 */
	List<Term> getCurrentTerms();

	/**
	 * Query the Academic Calendar Service for terms that have offering's
	 * published, determine the last ATP, and return its ID.
	 * 
	 * @return The ID of the last scheduled ATP.
	 * @throws RuntimeException
	 *             if the query fails or if the return data set doesn't make
	 *             sense.
	 */
	Term getLastScheduledTerm();

	/**
	 * Gets the ATP ID of the first ATP in the current academic year.
	 */
	Term getFirstTermOfAcademicYear(YearTerm yearTerm);

    /**
     * Gets the number of terms in a academic year.
     *
     * @param yearTerm
     * @return
     */
    public int getNumberOfTermsInAcademicYear(YearTerm yearTerm);

    /**
     * Gets a list of Terms in the academic year.
     *
     * @param yearTerm
     * @return
     */
    public List<Term> getTermsInAcademicYear(YearTerm yearTerm);

    /**
     * Gets the number of terms in a academic year.
     *
     * @return
     */
    public int getNumberOfTermsInAcademicYear();

    /**
     * Gets a list of Terms in the academic year.
     *
     * @return
     */
    public List<Term> getTermsInAcademicYear();

    /**
     * Gets the term name of a term in the academic year.
     * @param index
     * @return
     */
    public String getTermNameInAcadmicYear(int index);



	/**
	 * Returns true if an ATP is considered present or greater in the context of
	 * WHAT? Otherwise, false.
	 * 
	 * @param termId
	 * @return
	 */
	boolean isPlanning(String atpId);

	/**
	 * Returns true if an ATP is considered present or greater in the context of
	 * the current term's term. Otherwise, false.
	 * 
	 * @param termId
	 * @return
	 */
	boolean isCompleted(String atpId);

	/**
	 * Determines whether a course is in a specific term.
	 * 
	 * @param term
	 * @param course
	 * @return
	 */
	boolean isCourseOffered(Term term, Course course);

	/**
	 * Gets a list of published terms.
	 * 
	 * @return
	 */
	List<Term> getPublishedTerms();

	/**
	 * 
	 * @param yearTerm
	 *            - Year and term storage object
	 * @return termId created form object information
	 */
	Term getTerm(YearTerm yearTerm);

	/**
	 * 
	 * @param termId
	 *            - Id of a term
	 * @return YearTerm holding information for the term
	 */
	YearTerm getYearTerm(Term term);


}