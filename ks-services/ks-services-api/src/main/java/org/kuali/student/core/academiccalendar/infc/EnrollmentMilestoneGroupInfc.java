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
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

public interface EnrollmentMilestoneGroupInfc {

    /**
     * Name: RegistrationStart
     * Gets the registration start date.
     *
     * @return the registration start date
     */
    public Date getRegistrationStartDate();

    /**
     * Name: RegistrationEnd
     * Gets the registration end date.
     *
     * @return the registration end date
     */
    public Date getRegistrationEndDate();

    /**
     * Name: ClassStart
     * Gets the class start date.
     *
     * @return the class start date
     */
    public Date getClassStartDate();

    /**
     * Name: ClassEnd
     * Gets the class end date.
     *
     * @return the class end date
     */
    public Date getClassEndDate();

    /**
     * Name: AddDate
     * Gets the add date.
     *
     * @return add date
     */
    public Date getAddDate();

    /**
     * Name: DropDate
     * Gets the drop date.
     *
     * @return drop date
     */
    public Date getDropDate();

    /**
     * Name: FinalExamStart
     * Gets the final exam start date.
     *
     * @return the final exam start date
     */
    public Date getFinalExamStartDate();

    /**
     * Name: FinalExamEnd
     * Gets the final exam end date.
     *
     * @return the final exam end date
     */
    public Date getFinalExamEndDate();

    /**
     * Name: GradingStart
     * Gets the grading period start date.
     *
     * @return the grading start date
     */
    public Date getGradingStartDate();

    /**
     * Name: GradingEnd
     * Gets the grading period end date.
     *
     * @return the grading end date
     */
    public Date getGradingEndDate();

}
