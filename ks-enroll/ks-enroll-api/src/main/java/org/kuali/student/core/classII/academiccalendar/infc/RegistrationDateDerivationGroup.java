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


/**
 * This structure specifies how the dates in the RegistrationDateGroup
 * are derived.
 *
 * @Author tom
 * @Since Tue Apr 23 14:22:34 EDT 2011
 */ 

public interface RegistrationDateDerivationGroup {

    /**
     * Name: Registration Start Date Term Key
     * Gets the key of the Term from which the start of the
     * registration period is derived. If null, then the registration
     * start date needs to be explicitly set.
     *
     * @return a term key
     */
    public String getRegistrationStartDateTermKey();

    /**
     * Name: Registration End Date Term Key
     * Gets the key of the Term from which the end of the registration
     * period is derived. If null, then the registration end date
     * needs to be explicitly set.
     *
     * @return a term key
     */
    public String getRegistrationEndDateTermKey();

    /**
     * Name: Class Start Date Term Key
     * Gets the key of the Term from which the start of the class
     * period is derived. If null, then the class start date needs to
     * be explicitly set.
     *
     * @return a term key
     */
    public String getClassStartDateTermKey();

    /**
     * Name: Class End Date Term Key
     * Gets the key of the Term from which the end of the class period
     * is derived. If null, then the class end date needs to be
     * explicitly set.
     *
     * @return a term key
     */
    public String getClassEndDateTermKey();

    /**
     * Name: Add Date Term Key
     * Gets the key of the Term from which the add date is derived. If
     * null, then the add date needs to be explicitly set.
     *
     * @return a term key
     */
    public String getAddDateTermKey();

    /**
     * Name: Drop Date Term Key
     * Gets the key of the Term from which the drop date is derived. If
     * null, then the drop date needs to be explicitly set.
     *
     * @return a term key
     */
    public String getDropDateTermKey();

    /**
     * Name: Final Exam Start Date Term Key
     * Gets the key of the Term from which the start of the final exam
     * period is derived. If null, then the class start date needs to
     * be explicitly set.
     *
     * @return a term key
     */
    public String getFinalExamStartDateTermKey();

    /**
     * Name: Final Exam End Date Term Key
     * Gets the key of the Term from which the end of the final exam
     * period is derived. If null, then the class end date needs to be
     * explicitly set.
     *
     * @return a term key
     */
    public String getFinalExamEndDateTermKey();

    /**
     * Name: Grading Start Date Term Key
     * Gets the key of the Term from which the start of the grading
     * period is derived. If null, then the class start date needs to
     * be explicitly set.
     *
     * @return a term key
     */
    public String getGradingStartDateTermKey();

    /**
     * Name: Grading End Date Term Key
     * Gets the key of the Term from which the end of the grading
     * period is derived. If null, then the class end date needs to be
     * explicitly set.
     *
     * @return a term key
     */
    public String getGradingEndDateTermKey();
}
