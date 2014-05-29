/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.ap.framework.context;

import org.kuali.student.r2.core.acal.infc.AcademicCalendar;
import org.kuali.student.r2.core.acal.infc.Term;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
	 * @return The list of the current ATP.
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

    /**
     * Gets the current term based on the current date.
     *
     * @return Current Term
     */
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

    /**
     * Query the Academic Calendar Service, determine the current ATP based on a
     * specified key date, and return the list of the current terms.
     *
     * @return The list of the current ATP.
     */
    public List<Term>getCurrentTermsBasedOnKeyDate();

    /**
     * Get a list of the current Academic Terms based on specified Key Date and
     * make sure the SOC state associated with the term is published, and return
     * the list of the terms.
     * @return - A list of current terms
     */
    public List<Term> getCurrentTermsWithPublishedSOC ();

    /**
     * Get a list of the future Academic Terms and make sure the SOC state associated
     * with the term is published, and return the list of the terms.
     * @return - A list of future terms
     */
    public List<Term> getFutureTermsWithPublishedSOC ();

    /**
     * Get the term id that is at the beginning of the current academic year
     * @return The term id
     */
    public String getFirstTermIdOfCurrentAcademicYear();

    static enum ProjectedOrder {
        AU, FA, WI, SP, SU;
    }

    static enum ScheduledOrder {
        WI, SP, SU, AU, FA;
    }

    /**
     * Comparator for sorting the facet values of Terms
     * TODO: KSAP-1350 - Refactor and make configurable
     */
    public static final Comparator<String> TERMS = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {

            // Compare terms for nulls
            if (o1 == null && o2 == null)
                return 0;
            if (o1 == null)
                return -1;
            if (o2 == null)
                return 1;
            if (o1.equals(o2))
                return 0;
            if ("Unknown".equals(o1) || "None".equals(o1))
                return 1;
            if ("Unknown".equals(o2) || "None".equals(o2))
                return -1;

            String[] s1 = o1.split(" ");
            String[] s2 = o2.split(" ");
            if (s1.length != 2 && s2.length != 2)
                return o1.compareTo(o2);
            if (s1.length != 2)
                return 1;
            if (s2.length != 2)
                return -1;

            int year1;
            try {
                year1 = Integer.parseInt(s1[1]);
                if (year1 < 0 || year1 > 100)
                    year1 = -1;
            } catch (NumberFormatException e) {
                year1 = -1;
            }
            int year2;
            try {
                year2 = Integer.parseInt(s2[1]);
                if (year2 < 0 || year2 > 100)
                    year2 = -1;
            } catch (NumberFormatException e) {
                year2 = -1;
            }

            if (year1 != -1 && year2 != -1) {
                if (year1 < year2)
                    return -1;
                if (year1 > year2)
                    return 1;
                ScheduledOrder so1;
                try {
                    so1 = Enum.valueOf(ScheduledOrder.class, s1[0]);
                } catch (IllegalArgumentException e) {
                    so1 = null;
                }
                ScheduledOrder so2;
                try {
                    so2 = Enum.valueOf(ScheduledOrder.class, s2[0]);
                } catch (IllegalArgumentException e) {
                    so2 = null;
                }
                if (so1 == null && so2 == null)
                    return 0;
                if (so1 == null)
                    return 1;
                if (so2 == null)
                    return -1;
                return so1.compareTo(so2);
            }
            if (year1 != -1)
                return -1;
            if (year2 != -1)
                return 1;

            ProjectedOrder po1;
            try {
                po1 = Enum.valueOf(ProjectedOrder.class, s1[1]);
            } catch (IllegalArgumentException e) {
                po1 = null;
            }
            ProjectedOrder po2;
            try {
                po2 = Enum.valueOf(ProjectedOrder.class, s2[1]);
            } catch (IllegalArgumentException e) {
                po2 = null;
            }
            if (po1 == null && po2 == null)
                return 0;
            if (po1 == null)
                return 1;
            if (po2 == null)
                return -1;
            return po1.compareTo(po2);
        }
    };
}