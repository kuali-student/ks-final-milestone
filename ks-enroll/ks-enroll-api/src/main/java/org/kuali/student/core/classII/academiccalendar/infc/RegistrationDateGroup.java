/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.core.classII.academiccalendar.infc;

import java.util.Date;

import org.kuali.student.r2.common.infc.DateRange;


/**
 * A cluster of hardened dates pertinent to an academic term.
 *
 * A Date Group is a short cut to accessing a set of KeyDates dates
 * relating to a Term. An RegistrationDateGroup is available for all
 * Terms by default and does not explicitly need to be created.
 *
 * The dates in this group map to KeyDates. If a KeyDate does not
 * exist, then the return valueis null. An update of this structure
 * will update the corresponding KeyDate if it exists, or create one
 * of the appropriate Type if it does not exist.
 *
 * The KeyDate Type is used to link a specific KeyDate with one of the
 * elements in this structure. For example, getRegistrationStartDate()
 * returns the starting date of the range in a KeyDate of type
 * kuali.apt.milestone.RegistrationPeriod for the associated term.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

public interface RegistrationDateGroup {

    /**
     * Name: Registration Date Derivation Group
     * Gets the date derivation structure for the Registration
     * dats.
     *
     * @return the registratiob derivation date group
     */
    public RegistrationDateDerivationGroup getRegistrationDateDerivationGroup();

    /**
     * Name: Term Key
     * Gets the key identifying the term for this date group.
     *
     * @return the term key
     */
    public String getTermKey();

    /**
     * Name: Registration Date Range
     * Gets the registration period.
     *
     * @return the registration period
     */
    public DateRange getRegistrationDateRange();

    /**
     * Name: Class Date Range
     * Gets the instructional class period.
     *
     * @return the class period
     */
    public DateRange getClassDateRange();

    /**
     * Name: Add Date
     * Last Date to Add a Course
     *
     * @return add date
     */
    public Date getAddDate();

    /**
     * Name: Drop Date
     * Last date to drop a course
     *
     * @return drop date
     */
    public Date getDropDate();

    /**
     * Name: Final Exam Date Range
     * Gets the he Final Exam Period.
     *
     * @return the final exam period
     */
    public DateRange getFinalExamDateRange();

    /**
     * Name: Grading Date Range
     * Gets the grading period.
     *
     * @return the grading period
     */
    public DateRange getGradingDateRange();
}
