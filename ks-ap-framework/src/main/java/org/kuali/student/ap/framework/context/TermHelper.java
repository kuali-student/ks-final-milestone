package org.kuali.student.ap.framework.context;

import java.util.Date;
import java.util.List;

import org.kuali.student.r2.core.acal.infc.AcademicCalendar;
import org.kuali.student.r2.core.acal.infc.Term;
/**
 * Provides access to common ATP functionality.
 */
public interface TermHelper {

	/**
	 * Front-load term and academic calendar data for the planner based on the
	 * earliest ATP ID.
	 * 
	 * <p>
	 * This method should have no impact on TermHelper functionality, but when
	 * implemented will improve performance by loading all term data used by the
	 * planner into the current transaction.
	 * </p>
	 * 
	 * @param firstAtpId
	 *            The earliest ATP ID in the planner.
	 */
	void frontLoadForPlanner(String firstAtpId);

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
	 * Query the Academic Calendar Service for terms that have offerings
	 * official, determine the last ATP, and return its ID.
	 * 
	 * @return The ID of the last scheduled ATP.
	 * @throws RuntimeException
	 *             if the query fails or if the return data set doesn't make
	 *             sense.
	 */
	Term getLastScheduledTerm();

	/**
	 * Determine the oldest term to consider when performing historical lookups.
	 * 
	 * @return The ID of oldest term to consider when performing historical
	 *         lookups.
	 */
	Term getOldestHistoricalTerm();

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
	 * 
	 * @param index
	 * @return
	 */
    @Deprecated
	public String getTermNameInAcadmicYear(int index);

	/**
	 * Returns true if a term is available for planning.
	 * 
	 * @param termId
	 *            The term ID.
	 * @return True if the term is available for planning, false if not.
	 */
	boolean isPlanning(String termId);

	/**
	 * Returns true if a term official on the schedule of classes.
	 * 
	 * @param termId
	 *            The term ID.
	 * @return True if the term is official on the schedule of classes, false
	 *         if not.
	 */
	boolean isOfficial(String termId);

	/**
	 * Returns true if an ATP is considered present or greater in the context of
	 * the current term's term. Otherwise, false.
	 * 
	 * @param atpId
	 * @return
	 */
	boolean isCompleted(String atpId);



	/**
	 * Gets a list of official terms.
	 * 
	 * @return
	 */
	List<Term> getOfficialTerms();

	/**
	 * Gets a list of planning terms.
	 * 
	 * @return
	 */
	List<Term> getPlanningTerms();

	/**
	 * Gets a list of terms by date range.
	 *
	 * @return A list of terms, by date range.
	 */
    @Deprecated
	List<Term> getTermsByDateRange(Date startDate, Date endDate);

	/**
	 * 
	 * @param yearTerm
	 *            - Year and term storage object
	 * @return termId created form object information
	 */
	Term getTerm(YearTerm yearTerm);

	/**
	 * 
	 * @param term - term obj
	 * @return YearTerm holding information for the term
	 */
	YearTerm getYearTerm(Term term);

    public String findTermIdByNameAndContainingDates
            (Date termBeginDate, Date termEndDate, String termName);

    /**
     * Gets the current term based on the current date.
     *
     * @return Current Term
     */
    @Deprecated
    public Term getCurrentTerm();

    /**
     * Gets the current academic calendar based on the current date
     *
     * @return Current academic calendar
     */
    @Deprecated
    public AcademicCalendar getCurrentAcademicCalendar();

    /**
     * Sort a list by its start date
     * @param terms - List of Terms to be sorted
     * @param ascending - If True sort ascending, else sort descending
     * @return - A list of sorted terms
     */
    public List<Term> sortTermsByStartDate(List<Term> terms, boolean ascending);

    /**
     * Sort a list by its end date
     * @param terms - List of Terms to be sorted
     * @param ascending - If True sort ascending, else sort descending
     * @return - A list of sorted terms
     */
    public List<Term> sortTermsByEndDate(List<Term> terms, boolean ascending);
}