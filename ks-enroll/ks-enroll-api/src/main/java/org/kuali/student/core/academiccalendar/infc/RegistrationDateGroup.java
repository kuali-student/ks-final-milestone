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

package org.kuali.student.core.academiccalendar.infc;

import java.util.Date;


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
     * Name: Term Key
     *
     * The key identifying the term for this date group.
     *
     * @return the term key
     */
    public String getTermKey();

    /**
     * Name: Registration Start Date
     *
     * The beginning of the registration period
     *
     * @return the registration start date
     */
    public Date getRegistrationStartDate();

    /**
     * Name: Registration End Date
     * Gets the registration end date.
     *
     * @return the registration end date
     */
    public Date getRegistrationEndDate();

    /**
     * Name: Classes Start Date
     * Gets the class start date.
     *
     * @return the class start date
     */
    public Date getClassStartDate();

    /**
     * Name: Class End
     *
     * End of Classes
     *
     * @return the class end date
     */
    public Date getClassEndDate();

    /**
     * Name: Add Date
     *
     * Last Date to Add a Course
     *
     * @return add date
     */
    public Date getAddDate();

    /**
     * Name: Drop Date
     *
     * Last date to drop a course
     *
     * @return drop date
     */
    public Date getDropDate();

    /**
     * Name: Final Exam Start Date
     *
     * Beginning of the Final Exam Period
     *
     * @return the final exam start date
     */
    public Date getFinalExamStartDate();

    /**
     * Name: Final Exam End
     *
     * The end of the final exam period
     *
     * @return the final exam end date
     */
    public Date getFinalExamEndDate();

    /**
     * Name: Grading Start Date
     *
     * Beginnig of Grading period.
     *
     * @return the grading start date
     */
    public Date getGradingStartDate();

    /**
     * Name: Grading End Date
     *
     * Last day to submit grades without them being considered late.
     *
     * @return the grading end date
     */
    public Date getGradingEndDate();
}
