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

package org.kuali.student.ap.framework.context.decorators;

import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.r2.core.acal.infc.AcademicCalendar;
import org.kuali.student.r2.core.acal.infc.Term;

import java.util.Date;
import java.util.List;


public class TermHelperDecorator implements TermHelper {

    private TermHelper nextDecorator;

    public TermHelper getNextDecorator(){
        if (null == nextDecorator) {
            throw new RuntimeException("Misconfigured application: nextDecorator is null");
        }

        return nextDecorator;
    }

    public void setNextDecorator(TermHelper nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public void frontLoadForPlanner(String firstAtpId) {
        getNextDecorator().frontLoadForPlanner(firstAtpId);
    }

    @Override
    public Term getTerm(String atpId) {
        return getNextDecorator().getTerm(atpId);
    }

    @Override
    public YearTerm getYearTerm(String atpId) {
        return getNextDecorator().getYearTerm(atpId);
    }

    @Override
    public List<Term> getCurrentTerms() {
        return getNextDecorator().getCurrentTerms();
    }

    @Override
    public Term getLastScheduledTerm() {
        return getNextDecorator().getLastScheduledTerm();
    }

    @Override
    public Term getOldestHistoricalTerm() {
        return getNextDecorator().getOldestHistoricalTerm();
    }

    @Override
    public Term getFirstTermOfAcademicYear(YearTerm yearTerm) {
        return getNextDecorator().getFirstTermOfAcademicYear(yearTerm);
    }

    @Override
    public int getNumberOfTermsInAcademicYear(YearTerm yearTerm) {
        return getNextDecorator().getNumberOfTermsInAcademicYear(yearTerm);
    }

    @Override
    public List<Term> getTermsInAcademicYear(YearTerm yearTerm) {
        return getNextDecorator().getTermsInAcademicYear(yearTerm);
    }

    @Override
    public int getNumberOfTermsInAcademicYear() {
        return getNextDecorator().getNumberOfTermsInAcademicYear();
    }

    @Override
    public List<Term> getTermsInAcademicYear() {
        return getNextDecorator().getTermsInAcademicYear();
    }

    @Override
    public String getTermNameInAcadmicYear(int index) {
        return getNextDecorator().getTermNameInAcadmicYear(index);
    }

    @Override
    public boolean isPlanning(String termId) {
        return getNextDecorator().isPlanning(termId);
    }

    @Override
    public boolean isOfficial(String termId) {
        return getNextDecorator().isOfficial(termId);
    }

    @Override
    public boolean isCompleted(String termId) {
        return getNextDecorator().isCompleted(termId);
    }

    @Override
    public boolean isInProgress(String termId) {
        return getNextDecorator().isInProgress(termId);
    }

    @Override
    public boolean isFutureTerm(String termId) {
        return getNextDecorator().isFutureTerm(termId);
    }

    @Override
    public boolean isCurrentTerm(String termId) {
        return getNextDecorator().isCurrentTerm(termId);
    }

    @Override
    public boolean isRegistrationOpen(String termId) {
        return getNextDecorator().isRegistrationOpen(termId);
    }

    @Override
    public List<Term> getOfficialTerms() {
        return getNextDecorator().getOfficialTerms();
    }

    @Override
    public List<Term> getPlanningTerms() {
        return getNextDecorator().getPlanningTerms();
    }

    @Override
    public List<Term> getTermsByDateRange(Date startDate, Date endDate) {
        return getNextDecorator().getTermsByDateRange(startDate,endDate);
    }

    @Override
    public Term getTerm(YearTerm yearTerm) {
        return getNextDecorator().getTerm(yearTerm);
    }

    @Override
    public YearTerm getYearTerm(Term term) {
        return getNextDecorator().getYearTerm(term);
    }

    @Override
    public Term getCurrentTerm() {
        return getNextDecorator().getCurrentTerm();
    }

    @Override
    public AcademicCalendar getCurrentAcademicCalendar() {
        return getNextDecorator().getCurrentAcademicCalendar();
    }

    @Override
    public List<Term> sortTermsByStartDate(List<Term> terms, boolean ascending) {
        return getNextDecorator().sortTermsByStartDate(terms,ascending);
    }

    @Override
    public List<Term> sortTermsByEndDate(List<Term> terms, boolean ascending) {
        return getNextDecorator().sortTermsByEndDate(terms,ascending);
    }

    @Override
    public List<Term> sortTermsBySocReleaseDate(List<Term> terms, boolean ascending) {
        return getNextDecorator().sortTermsBySocReleaseDate(terms,ascending);
    }

    @Override
    public List<Term> getCurrentTermsBasedOnKeyDate() {
        return getNextDecorator().getCurrentTermsBasedOnKeyDate();
    }

    @Override
    public List<Term> getCurrentTermsWithPublishedSOC() {
        return getNextDecorator().getCurrentTermsWithPublishedSOC();
    }

    @Override
    public List<Term> getFutureTermsWithPublishedSOC() {
        return getNextDecorator().getFutureTermsWithPublishedSOC();
    }

    @Override
    public String getFirstTermIdOfCurrentAcademicYear() {
        return getNextDecorator().getFirstTermIdOfCurrentAcademicYear();
    }
}
