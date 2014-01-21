package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.r2.core.acal.infc.AcademicCalendar;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class TermHelperMockTest implements TermHelper {
    /**
     * Front-load term and academic calendar data for the planner based on the
     * earliest ATP ID.
     * <p/>
     * <p>
     * This method should have no impact on TermHelper functionality, but when
     * implemented will improve performance by loading all term data used by the
     * planner into the current transaction.
     * </p>
     *
     * @param firstAtpId The earliest ATP ID in the planner.
     */
    @Override
    public void frontLoadForPlanner(String firstAtpId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Look up a term by ATP ID.
     *
     * @param atpId The ATP ID for the term.
     * @return The term corresponding to the given ATP ID.
     */
    @Override
    public Term getTerm(String atpId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Look up a year/term by ATP ID.
     *
     * @param atpId The ATP ID for the term.
     * @return The term corresponding to the given ATP ID.
     */
    @Override
    public YearTerm getYearTerm(String atpId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Query the Academic Calendar Service, determine the current ATP, and
     * return the ID.
     *
     * @return The ID of the current ATP.
     */
    @Override
    public List<Term> getCurrentTerms() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Query the Academic Calendar Service for terms that have offerings
     * published, determine the last ATP, and return its ID.
     *
     * @return The ID of the last scheduled ATP.
     * @throws RuntimeException if the query fails or if the return data set doesn't make
     *                          sense.
     */
    @Override
    public Term getLastScheduledTerm() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Determine the oldest term to consider when performing historical lookups.
     *
     * @return The ID of oldest term to consider when performing historical
     *         lookups.
     */
    @Override
    public Term getOldestHistoricalTerm() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the ATP ID of the first ATP in the current academic year.
     */
    @Override
    public Term getFirstTermOfAcademicYear(YearTerm yearTerm) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the number of terms in a academic year.
     *
     * @param yearTerm
     * @return
     */
    @Override
    public int getNumberOfTermsInAcademicYear(YearTerm yearTerm) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets a list of Terms in the academic year.
     *
     * @param yearTerm
     * @return
     */
    @Override
    public List<Term> getTermsInAcademicYear(YearTerm yearTerm) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the number of terms in a academic year.
     *
     * @return
     */
    @Override
    public int getNumberOfTermsInAcademicYear() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets a list of Terms in the academic year.
     *
     * @return
     */
    @Override
    public List<Term> getTermsInAcademicYear() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the term name of a term in the academic year.
     *
     * @param index
     * @return
     */
    @Override
    public String getTermNameInAcadmicYear(int index) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns true if a term is available for planning.
     *
     * @param termId The term ID.
     * @return True if the term is available for planning, false if not.
     */
    @Override
    public boolean isPlanning(String termId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns true if a term published on the schedule of classes.
     *
     * @param termId The term ID.
     * @return True if the term is published on the schedule of classes, false
     *         if not.
     */
    @Override
    public boolean isOfficial(String termId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns true if an ATP is considered present or greater in the context of
     * the current term's term. Otherwise, false.
     *
     * @param atpId
     * @return
     */
    @Override
    public boolean isCompleted(String atpId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets a list of published terms.
     *
     * @return
     */
    @Override
    public List<Term> getOfficialTerms() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets a list of planning terms.
     *
     * @return
     */
    @Override
    public List<Term> getPlanningTerms() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets a list of terms by date range.
     *
     * @return A list of terms, by date range.
     */
    @Override
    public List<Term> getTermsByDateRange(Date startDate, Date endDate) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param yearTerm - Year and term storage object
     * @return termId created form object information
     */
    @Override
    public Term getTerm(YearTerm yearTerm) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param term - a term
     * @return YearTerm holding information for the term
     */
    @Override
    public YearTerm getYearTerm(Term term) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the current term based on the current date.
     *
     * @return Current Term
     */
    @Override
    public Term getCurrentTerm() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the current academic calendar based on the current date
     *
     * @return Current academic calendar
     */
    @Override
    public AcademicCalendar getCurrentAcademicCalendar() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sort a list by its start date
     *
     * @param terms     - List of Terms to be sorted
     * @param ascending - If True sort ascending, else sort descending
     * @return - A list of sorted terms
     */
    @Override
    public List<Term> sortTermsByStartDate(List<Term> terms, boolean ascending) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sort a list by its end date
     *
     * @param terms     - List of Terms to be sorted
     * @param ascending - If True sort ascending, else sort descending
     * @return - A list of sorted terms
     */
    @Override
    public List<Term> sortTermsByEndDate(List<Term> terms, boolean ascending) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get a list of the current Academic Terms and make sure the SOC state associated
     * with the term is published, and return the list of the terms.
     * @return - A list of current terms
     */
    public List<Term> getCurrentTermsWithPublishedSOC (){
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get a list of the future Academic Terms and make sure the SOC state associated
     * with the term is published, and return the list of the terms.
     * @return - A list of future terms
     */
    public List<Term> getFutureTermsWithPublishedSOC (){
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
