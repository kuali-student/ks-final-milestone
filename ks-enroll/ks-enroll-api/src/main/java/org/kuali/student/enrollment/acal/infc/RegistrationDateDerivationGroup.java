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

package org.kuali.student.enrollment.acal.infc;


/**
 * This structure specifies how (and if) the dates in the
 * RegistrationDateGroup are derived.
 *
 * This is a Work in Progress as it currently is only intended to
 * handle the derivation of dates of nested terms that start or end on
 * the same exact boundary days as the containing term.
 *
 * For example: the start date of classes for the Fall term is the
 * same for courses that run the entire term as it is for courses that
 * run for only the first half of the term.  This tells us that the
 * derivation of that classes start date in the half-sememster should
 * be the same as the classes start date in the overall semester.
 * 
 * @author tom
 * @since Tue Apr 23 14:22:34 EDT 2011
 */ 

public interface RegistrationDateDerivationGroup {

    /**
     * Gets the key of the Term from which the start of the
     * registration period is derived. If null, then the registration
     * start date needs to be explicitly set.
     *
     * @return a term key
     * @name Registration Start Date Term Key
     * @readOnly on update
     */
    public String getRegistrationStartDateTermKey();

    /**
     * Gets the key of the Term from which the end of the registration
     * period is derived. If null, then the registration end date
     * needs to be explicitly set.
     *
     * @return a term key
     * @name Registration End Date Term Key
     */
    public String getRegistrationEndDateTermKey();

    /**
     * Gets the key of the Term from which the start of the class
     * period is derived. If null, then the class start date needs to
     * be explicitly set.
     *
     * @return a term key
     * @name Class Start Date Term Key
     */
    public String getClassStartDateTermKey();


    /**
     * Gets the key of the Term from which the end of the class period
     * is derived. If null, then the class end date needs to be
     * explicitly set.
     *
     * @return a term key
     * @name Class End Date Term Key
     */
    public String getClassEndDateTermKey();
    
    /**
     * Gets the key of the Term from which the add date is derived. If
     * null, then the add date needs to be explicitly set.
     *
     * @return a term key
     * @name Add Date Term Key
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
     * Gets the key of the Term from which the end of the final exam
     * period is derived. If null, then the class end date needs to be
     * explicitly set.
     *
     * @return a term key
     * @name Final Exam End Date Term Key
     */
    public String getFinalExamEndDateTermKey();
 
    /** 
     * Gets the key of the Term from which the start of the grading
     * period is derived. If null, then the class start date needs to
     * be explicitly set.
     *
     * @return a term key
     * @name Grading Start Date Term Key
     */
    public String getGradingStartDateTermKey();
 
    /**
     * Gets the key of the Term from which the end of the grading
     * period is derived. If null, then the class end date needs to be
     * explicitly set.
     *
     * @return a term key
     * @name Grading End Date Term Key
     */
    public String getGradingEndDateTermKey();
 
}
